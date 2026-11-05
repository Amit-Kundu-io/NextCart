/**
 * File: Product.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.catalog.product

import androidx.compose.runtime.Immutable

@Immutable
data class ProductState(
    val isLoading: Boolean = false,
    val error: String = "",
)
