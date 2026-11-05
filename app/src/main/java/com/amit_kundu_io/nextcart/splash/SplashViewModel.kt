/**
 * File: SplashViewModel.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.nextcart.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit_kundu_io.database.MyDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class SplashViewModel(
    private val dataStore: MyDataStore
) : ViewModel() {

    private val _uiState = MutableLiveData(SplashUiState())
    val uiState: LiveData<SplashUiState> = _uiState

    init {
        viewModelScope.launch {
            val token = dataStore.token.firstOrNull()

            _uiState.value = SplashUiState(isLogin = !token.isNullOrBlank())
        }
    }
}