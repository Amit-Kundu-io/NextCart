package com.amit_kundu_io.network.RefreshToken

import com.amit_kundu_io.database.MyDataStore
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.cancellation.CancellationException

class RefreshTokenManager(
    private val refreshApi: RefreshApi,
    private val dataStore: MyDataStore
) {

    private val mutex = Mutex()

    suspend fun refreshToken(): Boolean {

        val oldAccessToken = dataStore.token.firstOrNull()

        return mutex.withLock {

            // Another request already refreshed the token
            val currentAccessToken = dataStore.token.firstOrNull()

            if (!currentAccessToken.isNullOrBlank() && currentAccessToken != oldAccessToken) {
                return@withLock true
            }

            // Get refresh token
            val refreshToken = dataStore.refreshToken.firstOrNull()

            if (refreshToken.isNullOrBlank()) {
                return@withLock false
            }

            try {
                val response = refreshApi.refreshToken(RefreshTokenRequest(refreshToken))

                // Save new tokens
                dataStore.setToken(response.accessToken)
                response.refreshToken?.let { dataStore.setRefreshToken(it) }

                true

            } catch (e: CancellationException) {
                throw e

            } catch (e: ResponseException) {
                throw e
            } catch (e: Exception) {
                false
            }
        }
    }
}