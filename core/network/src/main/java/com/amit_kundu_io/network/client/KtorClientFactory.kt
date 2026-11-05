/*
 * KtorClientFactory.kt
 * Author: Amit Kundu
 */
package com.amit_kundu_io.network.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class KtorClientFactory {

    fun create(): HttpClient = HttpClient(OkHttp) {
        // Ktor throws ResponseException for non-2xx responses.
        expectSuccess = true

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    explicitNulls = false
                }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 25_000
            connectTimeoutMillis = 10_000
            socketTimeoutMillis = 25_000
        }

        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "dummyjson.com"
            }
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }

}
