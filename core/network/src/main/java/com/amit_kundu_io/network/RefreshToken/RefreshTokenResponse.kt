/**
 * RefreshTokenResponse.kt
 *
 * Author      : Amit Kundu
 */

package com.amit_kundu_io.network.RefreshToken


import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)

