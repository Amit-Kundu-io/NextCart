/**
 * RefreshTokenRequest.kt
 *
 * Author      : Amit Kundu
 * Created On  : 09/07/2026
 */

package com.amit_kundu_io.network.RefreshToken


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    @SerialName("refreshToken")
    val refreshToken: String
)