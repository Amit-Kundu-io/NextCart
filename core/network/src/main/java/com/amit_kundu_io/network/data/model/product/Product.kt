package com.amit_kundu_io.network.data.model.product

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val availabilityStatus: String? = null,
    val brand: String? = null,
    val category: String? = null,
    val description: String? = null,
    val dimensions: Dimensions? = null,
    val discountPercentage: Double = 0.0,
    val id: Long,
    val images: List<String>? = null,
    val meta: Meta? = null,
    val minimumOrderQuantity: Int? = null,
    val price: Double? = null,
    val rating: Double? = null,
    val returnPolicy: String? = null,
    val reviews: List<Review>? = null,
    val shippingInformation: String? = null,
    val sku: String? = null,
    val stock: Int? = null,
    val tags: List<String>? = null,
    val thumbnail: String? = null,
    val title: String? = null,
    val warrantyInformation: String? = null,
    val weight: Int? = null
)