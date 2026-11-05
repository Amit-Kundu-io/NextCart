package com.amit_kundu_io.network.data.model.product

import kotlinx.serialization.Serializable

@Serializable
data class Products(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)