/**
 * File: Product.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.catalog.product

import androidx.compose.runtime.Immutable
import com.amit_kundu_io.network.data.model.categorie.CategoriesItem
import com.amit_kundu_io.network.data.model.product.Product

@Immutable
data class ProductState(
    val isCategoryLoading: Boolean = false,
    val categoryError: String? = null,
    val categories: List<CategoriesItem> = emptyList(),
    val selectedCategory: String? = null,

    val isProductLoading: Boolean = false,
    val productsError: String? = null,
    val products: List<Product> = emptyList(),

    val searchQuery: String = "",
    val sortBy: String? = null,
    val order: String = "asc",

    val skip: Int = 0,
    val hasMoreProducts: Boolean = true
)