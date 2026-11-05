package com.amit_kundu_io.network.utilit

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val timestamp: String,
    val status: Int,
    val code: String,
    val message: String,
    val path: String
)
