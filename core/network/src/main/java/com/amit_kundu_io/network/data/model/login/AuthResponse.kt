/**
 * File: AuthResponse.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.network.data.model.login

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val id: Long,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String? = null,
    val image: String,
    val accessToken: String,
    val refreshToken: String
)