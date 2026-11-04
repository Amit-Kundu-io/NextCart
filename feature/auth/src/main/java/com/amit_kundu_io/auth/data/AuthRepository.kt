/**
 * File: AuthRepository.kt
 * Created by Amit Kundu
 * Created on 04-Nov-26
 */

package com.amit_kundu_io.auth.data

interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean
}