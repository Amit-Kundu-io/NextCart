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
    val accessTokenExpiresInSeconds: Int,
    val email: String,
    val refreshToken: String?,
    val role: String,
    val roleId: Int,
    val userId: Int
)

