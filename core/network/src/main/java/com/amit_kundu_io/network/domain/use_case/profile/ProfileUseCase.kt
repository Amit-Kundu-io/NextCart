/**
 * File: ProfileUseCase.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.network.domain.use_case.profile

import com.amit_kundu_io.database.MyDataStore
import com.amit_kundu_io.network.RefreshToken.RefreshTokenManager
import com.amit_kundu_io.network.RefreshToken.RefreshTokenResponse
import com.amit_kundu_io.network.data.model.user.User
import com.amit_kundu_io.network.domain.repo.CommonRepo
import com.amit_kundu_io.network.utilit.NetworkResource
import com.amit_kundu_io.network.utilit.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class ProfileUseCase(
    private val commonRepo: CommonRepo,
    private val dataStore: MyDataStore,
   private val tokenResponse: RefreshTokenManager

) {

    operator fun invoke(): Flow<NetworkResource<User>> = safeApiCall(
        refreshTokenRepo = tokenResponse,
        apiCall = {
            val tolken = dataStore.token.firstOrNull() ?: ""
            commonRepo.getMe(accessToken = tolken)
        },
    )

}