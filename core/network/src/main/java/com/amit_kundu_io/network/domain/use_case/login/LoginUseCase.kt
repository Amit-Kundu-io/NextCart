/**
 * File: LoginUseCase.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.network.domain.use_case.login

import com.amit_kundu_io.database.MyDataStore
import com.amit_kundu_io.network.data.model.login.AuthResponse
import com.amit_kundu_io.network.data.model.login.LoginRequest
import com.amit_kundu_io.network.domain.repo.CommonRepo
import com.amit_kundu_io.network.utilit.NetworkResource
import com.amit_kundu_io.network.utilit.safeApiCall
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val dataStore : MyDataStore,
    private val commonRepo: CommonRepo
) {
    operator fun invoke(
        username : String,
        password : String
    ) : Flow<NetworkResource<AuthResponse>> = safeApiCall(
        apiCall = {
            val req = LoginRequest(
                username =username ,
                password = password,
            )
            commonRepo.login(req)
        },
        onSuccess = { response ->
            dataStore.setUserId(response.id ?: 0)
            dataStore.setName(response.username ?: "")
            dataStore.setToken(response.accessToken ?: "")
            dataStore.setRefreshToken(response.refreshToken ?: "")
            dataStore.setEmail(response.email ?: "")
        },
    )
}