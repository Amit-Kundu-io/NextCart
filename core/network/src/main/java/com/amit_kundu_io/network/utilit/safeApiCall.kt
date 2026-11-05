package com.amit_kundu_io.network.utilit

import android.util.Log
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
    apiCall: suspend () -> T,
    onSuccess: suspend (T) -> Unit = {},
    onEmit: suspend FlowCollector<NetworkResource<T>>.(T) -> Unit = {
        emit(NetworkResource.Success(it))
    }
): Flow<NetworkResource<T>> = flow {
    emit(NetworkResource.Loading())

    try {
        val response = apiCall()


        onSuccess(response)
        onEmit(response)

    } catch (e: CancellationException) {
        throw e

    } catch (e: ResponseException) {

        emit(NetworkResource.Error(message = e.message))

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
