/**
 * File: SplashActivity.kt
 * Created by Amit Kundu
 * Created on 04-Nov-26
 */

package com.amit_kundu_io.nextcart.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.amit_kundu_io.auth.R
import com.amit_kundu_io.auth.login.LoginActivity
import com.amit_kundu_io.nextcart.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class SplashActivity : AppCompatActivity() {

    private val loginLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {
                // Open Compose MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            delay(1500)

            loginLauncher.launch(
                Intent(this@SplashActivity, LoginActivity::class.java)
            )
        }
    }
}