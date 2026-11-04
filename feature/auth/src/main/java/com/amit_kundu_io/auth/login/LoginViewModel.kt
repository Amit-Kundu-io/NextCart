/**
 * File: LoginViewModel.kt
 * Created by Amit Kundu
 * Created on 04-Nov-26
 */

package com.amit_kundu_io.auth.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit_kundu_io.auth.data.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(LoginUiState())
    val uiState: LiveData<LoginUiState> = _uiState

    // Update only the required state properties
    private fun updateState(
        update: (LoginUiState) -> LoginUiState
    ) {
        _uiState.value = update(
            _uiState.value ?: LoginUiState()
        )
    }

    fun login(email: String, password: String) {

        updateState {
            it.copy(
                emailError = null,
                passwordError = null,
                errorMessage = null,
                isSuccess = false
            )
        }

        // Validate email
        if (email.isBlank()) {
            updateState {
                it.copy(emailError = "Enter email")
            }
            return
        }

        // Validate password
        if (password.isBlank()) {
            updateState {
                it.copy(passwordError = "Enter password")
            }
            return
        }

        // Start login
        viewModelScope.launch {

            updateState {
                it.copy(isLoading = true)
            }

            try {
                val success = authRepository.login(email, password)

                updateState {
                    it.copy(
                        isLoading = false,
                        isSuccess = success,
                        errorMessage = if (success) {
                            null
                        } else {
                            "Invalid credentials"
                        }
                    )
                }

            } catch (exception: Exception) {

                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Something went wrong"
                    )
                }
            }
        }
    }
}