package com.amit_kundu_io.network.data.model.product

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val barcode: String,
    val createdAt: String,
    val qrCode: String,
    val updatedAt: String
)