/**
 * File: ProductDetails.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.catalog.product_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.amit_kundu_io.catalog.navigation.CatalogRoutes
import com.amit_kundu_io.network.domain.use_case.get_products.GetSingleProduct
import com.amit_kundu_io.network.utilit.NetworkResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ProductDetailsViewModel(
    private val getProductsUseCase: GetSingleProduct,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val route = savedStateHandle.toRoute<CatalogRoutes.ProductDetails>()

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ProductDetailsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                getProduct()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ProductDetailsState()
        )

    fun onAction(action: ProductDetailsAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

    fun getProduct() {
        getProductsUseCase.invoke(route.id).onEach { res ->

            when (res) {
                is NetworkResource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = res.message
                        )
                    }
                }

                is NetworkResource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                is NetworkResource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            product = res.data
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}