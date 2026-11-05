/**
 * File: Home.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.home.presentation.home

import androidx.compose.runtime.Immutable
import com.amit_kundu_io.network.data.model.product.Product

@Immutable
data class HomeState(
    val isLoading: Boolean = false,
    val error: String = "",

    val flashDeals : List<Product> = emptyList()  ,



    val isBestSellersLoading: Boolean = false,
    val bestSellersError: String = "",

    val bestSellers : List<Product> = emptyList()
)
