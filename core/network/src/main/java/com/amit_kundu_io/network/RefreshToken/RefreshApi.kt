/**
 * RefreshApi.kt
 *
 * Author      : Amit Kundu
 */

package com.amit_kundu_io.network.RefreshToken


interface RefreshApi {

    suspend fun refreshToken(
        request: RefreshTokenRequest
    ): RefreshTokenResponse

}