/**
 * File: Product.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.catalog.product

sealed interface ProductAction {

    data class SearchChanged(val query: String) : ProductAction

    data class CategorySelected(
        val category: String?
    ) : ProductAction

    data object LoadMore : ProductAction
    data object Refresh : ProductAction
}