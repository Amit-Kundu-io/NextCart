package com.example.nextcart.data

import com.amit_kundu_io.network.data.model.product.Dimensions
import com.amit_kundu_io.network.data.model.product.Meta
import com.amit_kundu_io.network.data.model.product.Product

object SampleData {
    private const val CDN = "https://cdn.dummyjson.com/product-images"

    private fun product(
        id: Int,
        title: String,
        price: Double,
        rating: Double,
        discount: Double,
        thumbnail: String,
    ) = Product(
        availabilityStatus = "In Stock",
        brand = "Essence",
        category = "beauty",
        description = "Sample description",
        dimensions = Dimensions(depth = 14.0, height = 13.0, width = 9.0),
        discountPercentage = discount,
        id = id,
        images = listOf(thumbnail),
        meta = Meta(
            barcode = "0000000000000",
            createdAt = "2025-01-01T00:00:00.000Z",
            qrCode = "",
            updatedAt = "2025-01-01T00:00:00.000Z",
        ),
        minimumOrderQuantity = 1,
        price = price,
        rating = rating,
        returnPolicy = "30 days return policy",
        reviews = emptyList(),
        shippingInformation = "Ships in 1 week",
        sku = "SKU-$id",
        stock = 99,
        tags = emptyList(),
        thumbnail = thumbnail,
        title = title,
        warrantyInformation = "1 month warranty",
        weight = 2,
    )

    val mascara = product(
        1, "Essence Mascara Lash Princess", 9.99, 4.94, 7.17,
        "$CDN/beauty/essence-mascara-lash-princess/thumbnail.webp"
    )
    val headphones = product(
        2, "Wireless Headphones", 49.99, 4.8, 0.0,
        "$CDN/mobile-accessories/apple-airpods-max-silver/thumbnail.webp"
    )

    val flashDeals = listOf(mascara.copy(title = "Essence Mascara"), headphones)
    val bestSellers = listOf(mascara)
    val newArrival = NewArrival(
        "New collection", "Explore newly added products",
        "$CDN/furniture/annibale-colombo-bed/thumbnail.webp"
    )
}
