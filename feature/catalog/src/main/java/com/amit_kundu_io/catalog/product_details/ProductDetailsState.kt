/**
 * File: ProductDetails.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.catalog.product_details

import androidx.compose.runtime.Immutable
import com.amit_kundu_io.network.data.model.product.Product

@Immutable
data class ProductDetailsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val product: Product? = null,
)
