package com.amit_kundu_io.network.utilit

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.time.Instant

private val apiErrorJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

suspend fun getApiError(exception: ResponseException): ApiErrorResponse {
    val response = exception.response
    val httpStatus = response.status.value
    val requestPath = response.call.request.url.encodedPath

    val rawBody = try {
        response.bodyAsText()
    } catch (e: CancellationException) {
        throw e
    } catch (_: Exception) {
        ""
    }

    val jsonObject = try {
        apiErrorJson.parseToJsonElement(rawBody).jsonObject
    } catch (_: Exception) {
        null
    }

    fun stringField(name: String): String? =
        try {
            jsonObject?.get(name)?.jsonPrimitive?.contentOrNull?.takeIf { it.isNotBlank() }
        } catch (_: Exception) {
            null
        }

    val status = try {
        jsonObject?.get("status")?.jsonPrimitive?.intOrNull
    } catch (_: Exception) {
        null
    } ?: httpStatus

    return ApiErrorResponse(
        timestamp = stringField("timestamp") ?: Instant.now().toString(),
        status = status,
        code = stringField("code") ?: "HTTP_$status",
        message = stringField("message") ?: exception.message ?: "Request failed (HTTP $status)",
        path = stringField("path") ?: requestPath
    )
}
