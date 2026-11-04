/**
 * File: ForgotPasswordActivity.kt
 * Created by Amit Kundu
 * Created on 04-Nov-26
 */

package com.amit_kundu_io.auth.forgotpassword


import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amit_kundu_io.auth.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val email = findViewById<TextInputEditText>(R.id.etEmail)
        val resetButton = findViewById<MaterialButton>(R.id.btnResetPassword)
        val backToLogin = findViewById<TextView>(R.id.tvBackToLogin)

        // Back to Login
        backToLogin.setOnClickListener {
            finish()
        }

        // Reset Password
        resetButton.setOnClickListener {

            val userEmail = email.text.toString().trim()

            when {
                userEmail.isEmpty() -> {
                    email.error = "Enter your email"
                }

                !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches() -> {
                    email.error = "Enter a valid email"
                }

                else -> {
                    Toast.makeText(
                        this,
                        "Password reset link sent successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}