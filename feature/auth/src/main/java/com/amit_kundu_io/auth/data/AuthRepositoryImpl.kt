/**
 * File: AuthRepositoryImpl.kt
 * Created by Amit Kundu
 * Created on 04-Nov-26
 */

package com.amit_kundu_io.auth.data

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {
        delay(3_000.milliseconds)
        return true
    }
}