/**
 * File: SplashActivity.kt
 * Created by Amit Kundu
 * Created on 04-Nov-26
 */

package com.amit_kundu_io.nextcart.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.amit_kundu_io.auth.R
import com.amit_kundu_io.auth.login.LoginActivity
import com.amit_kundu_io.nextcart.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val authViewModel: SplashViewModel by viewModel()
    private val loginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == RESULT_OK) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        observeAuthState()
    }

    private fun observeAuthState() {
        authViewModel.uiState.observe(this) { state ->

            when (state.isLogin) {

                null -> {
                    // Still checking DataStore.
                }

                true -> {
                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                    finish()
                }

                false -> {
                    loginLauncher.launch(
                        Intent(this, LoginActivity::class.java)
                    )
                }
            }
        }
    }
}