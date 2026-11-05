/**
 * File: Home.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit_kundu_io.network.domain.use_case.get_products.GetProductsUseCase
import com.amit_kundu_io.network.utilit.NetworkResource
import com.amit_kundu_io.network.utilit.NetworkUtils.API_ERROR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                flashDeals()
                bestSellers()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeState()
        )


    fun onAction(action: HomeAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }


    // Flash Deals

    fun flashDeals() {
        getProductsUseCase(
            limit = 10,
            sortBy = "discountPercentage",
            order = "desc"
        ).onEach { res ->
            when (res){
                is NetworkResource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = res.message ?: API_ERROR
                        )
                    }

                }

                is NetworkResource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                        )
                    }
                }

                is NetworkResource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            flashDeals = res.data?.products ?: emptyList()
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }



    // Best Sellers
    fun bestSellers() {
        getProductsUseCase(
            limit = 10,
            sortBy = "rating",
            order = "desc"
        ).onEach { res ->
            when (res){
                is NetworkResource.Error -> {
                    _state.update {
                        it.copy(
                            isBestSellersLoading = false,
                            bestSellersError = res.message ?: API_ERROR
                        )
                    }

                }

                is NetworkResource.Loading -> {
                    _state.update {
                        it.copy(
                            isBestSellersLoading = true,
                        )
                    }
                }

                is NetworkResource.Success -> {
                    _state.update {
                        it.copy(
                            isBestSellersLoading = false,
                            bestSellers = res.data?.products ?: emptyList()
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }



}