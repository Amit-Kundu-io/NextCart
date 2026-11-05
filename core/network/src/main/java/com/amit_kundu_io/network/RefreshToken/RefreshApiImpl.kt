/**
 * RefreshApiImpl.kt
 *
 * Author      : Amit Kundu
 */

package com.amit_kundu_io.network.RefreshToken


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody


class RefreshApiImpl(
    private val client: HttpClient
) : RefreshApi {

    override suspend fun refreshToken(
        request: RefreshTokenRequest
    ): RefreshTokenResponse {
        return client.post("/auth/refresh-token") {
            setBody(request)
        }.body()
    }
}