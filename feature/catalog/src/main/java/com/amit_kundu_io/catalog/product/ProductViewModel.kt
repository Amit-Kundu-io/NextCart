/**
 * File: Product.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.catalog.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit_kundu_io.network.domain.use_case.cagagory.GetCategoryUseCase
import com.amit_kundu_io.network.domain.use_case.get_products.GetProductsUseCase
import com.amit_kundu_io.network.utilit.NetworkResource
import com.amit_kundu_io.network.utilit.NetworkUtils.API_ERROR
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 20
        private const val SEARCH_DEBOUNCE = 400L
    }

    private val _state = MutableStateFlow(ProductState())
    val state = _state.asStateFlow()

    private var categoryJob: Job? = null
    private var productJob: Job? = null
    private var searchJob: Job? = null

    init {
        loadCategories()
    }

    fun onAction(action: ProductAction) {
        when (action) {
            is ProductAction.SearchChanged -> search(action.query)

            is ProductAction.CategorySelected -> selectCategory(action.category)

            ProductAction.LoadMore -> loadMore()

            ProductAction.Refresh -> refresh()
        }
    }

    // Fetch categories first
    private fun loadCategories() {
        categoryJob?.cancel()

        categoryJob = getCategoryUseCase()
            .onEach { result ->
                when (result) {
                    is NetworkResource.Loading -> {
                        _state.update {
                            it.copy(isCategoryLoading = true)
                        }
                    }

                    is NetworkResource.Success -> {
                        _state.update {
                            it.copy(
                                isCategoryLoading = false,
                                categoryError = null,
                                categories = result.data ?: emptyList()
                            )
                        }

                        loadProducts(reset = true)
                    }

                    is NetworkResource.Error -> {
                        _state.update {
                            it.copy(
                                isCategoryLoading = false,
                                categoryError = result.message ?: API_ERROR
                            )
                        }

                        // Still load all products if categories fail
                        loadProducts(reset = true)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    // Fetch products
    private fun loadProducts(reset: Boolean) {
        val current = _state.value

        if (!reset && (!current.hasMoreProducts || current.isProductLoading)) {
            return
        }

        productJob?.cancel()

        val currentSkip = if (reset) 0 else current.skip

        productJob = getProductsUseCase(
            limit = PAGE_SIZE,
            skip = currentSkip,
            search = current.searchQuery,
            sortBy = current.sortBy,
            order = current.order,
            category = current.selectedCategory
        ).onEach { result ->

            when (result) {
                is NetworkResource.Loading -> {
                    _state.update {
                        it.copy(
                            isProductLoading = true,
                            productsError = null
                        )
                    }
                }

                is NetworkResource.Success -> {
                    val newProducts = result.data?.products.orEmpty()

                    _state.update { old ->
                        val updatedProducts = if (reset) {
                            newProducts
                        } else {
                            (old.products + newProducts).distinctBy { it.id }
                        }

                        old.copy(
                            isProductLoading = false,
                            productsError = null,
                            products = updatedProducts,
                            skip = currentSkip + newProducts.size,
                            hasMoreProducts = newProducts.size == PAGE_SIZE
                        )
                    }
                }

                is NetworkResource.Error -> {
                    _state.update {
                        it.copy(
                            isProductLoading = false,
                            productsError = result.message ?: API_ERROR
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    // Search with debounce
    private fun search(query: String) {
        _state.update {
            it.copy(searchQuery = query)
        }

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE)
            loadProducts(reset = true)
        }
    }

    // Category selection
    private fun selectCategory(category: String?) {
        if (_state.value.selectedCategory == category) return

        _state.update {
            it.copy(selectedCategory = category)
        }

        loadProducts(reset = true)
    }

    // Pagination
    private fun loadMore() {
        loadProducts(reset = false)
    }

    // Refresh
    private fun refresh() {
        searchJob?.cancel()

        _state.update {
            it.copy(
                searchQuery = "",
                selectedCategory = null,
                sortBy = null,
                order = "asc",
                skip = 0,
                hasMoreProducts = true
            )
        }

        loadProducts(reset = true)
    }
}