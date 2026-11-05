/**
 * File: Profile.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit_kundu_io.network.domain.use_case.profile.ProfileUseCase
import com.amit_kundu_io.network.utilit.NetworkResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ProfileState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                loadProfile()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ProfileState()
        )

    fun onAction(action: ProfileAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

    fun loadProfile() {
        profileUseCase.invoke().onEach { res ->

            when (res) {
                is NetworkResource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = res.message
                        )
                    }
                }

                is NetworkResource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                is NetworkResource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            user = res.data
                        )
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

}