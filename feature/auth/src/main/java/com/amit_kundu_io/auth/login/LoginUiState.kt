/**
 * File: LoginUiState.kt
 * Created by Amit Kundu
 * Created on 04-Nov-26
 */

package com.amit_kundu_io.auth.login

import androidx.compose.runtime.Immutable


@Immutable
data class LoginUiState(
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)