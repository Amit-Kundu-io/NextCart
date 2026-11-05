package com.amit_kundu_io.network.utilit

import android.util.Log
import com.amit_kundu_io.network.RefreshToken.RefreshTokenManager
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.io.IOException

private const val TAG = "KTOR_HTTP_SAFE_API_CALL"


fun <T> safeApiCall(
    refreshTokenRepo: RefreshTokenManager? = null,
    apiCall: suspend () -> T,
    onSuccess: suspend (T) -> Unit = {},
    onEmit: suspend FlowCollector<NetworkResource<T>>.(T) -> Unit = {
        emit(NetworkResource.Success(it))
    }
): Flow<NetworkResource<T>> = flow {
    emit(NetworkResource.Loading())

    try {
        val response: T = try {
            apiCall()
        } catch (firstError: ResponseException) {
            val status = firstError.response.status.value

            // Refresh only for 401. A 403 is a permission error and must not refresh.
            if (status != 401 || refreshTokenRepo == null) {
                val apiError = getApiError(firstError)
                emit(NetworkResource.Error(message = apiError.message))
                return@flow
            }

            Log.d(TAG, "401 received; attempting token refresh.")

            val refreshed = try {
                refreshTokenRepo.refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, "Token refresh failed", e)
                false
            }

            if (!refreshed) {
                val originalError = getApiError(firstError)

                emit(NetworkResource.Error(message = originalError.message))
                return@flow
            }

            Log.d(TAG, "Token refreshed; retrying request once.")

            try {
                apiCall()
            } catch (retryError: ResponseException) {
                if (retryError.response.status.value == 401) {
                    val parsedError = getApiError(retryError)

                    emit(NetworkResource.Error(message = parsedError.message))
                    return@flow
                }

                // Other HTTP errors (including 403) are handled by the outer catch.
                throw retryError
            }
        }

        onSuccess(response)
        onEmit(response)

    } catch (e: CancellationException) {
        throw e

    } catch (e: ResponseException) {
        val apiError = getApiError(e)
        emit(NetworkResource.Error(message = apiError.message))

    } catch (e: ConnectTimeoutException) {

        emit(NetworkResource.Error(message = e.message))

    } catch (e: SocketTimeoutException) {
        emit(NetworkResource.Error(message = e.message))


    } catch (e: UnresolvedAddressException) {
        emit(NetworkResource.Error(message = e.message))


    } catch (e: IOException) {
        emit(NetworkResource.Error(message = e.message))


    } catch (e: Exception) {
        Log.e(TAG, "Unexpected API error", e)
        emit(NetworkResource.Error(message = e.message))

    }
}.flowOn(Dispatchers.IO)
