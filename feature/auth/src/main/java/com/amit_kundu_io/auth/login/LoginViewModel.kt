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
import com.amit_kundu_io.network.domain.use_case.login.LoginUseCase
import com.amit_kundu_io.network.utilit.NetworkResource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel(
    private val useCase: LoginUseCase
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

        //avat	avatpass

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

            useCase.invoke(email,password).onEach {res->
                when(res){
                    is NetworkResource.Error -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                errorMessage = res.message ?: "Something went wrong"
                            )
                        }
                    }
                    is NetworkResource.Loading -> {
                        updateState {
                            it.copy(isLoading = true)
                        }
                    }
                    is NetworkResource.Success -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                isSuccess = true,
                            )
                        }
                    }
                }


            }.launchIn(viewModelScope)

        }
    }
}