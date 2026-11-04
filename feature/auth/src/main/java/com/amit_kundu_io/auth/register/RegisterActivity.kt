/**
 * File: RegisterActivity.kt
 * Created by Amit Kundu
 * Created on 04-Nov-26
 */

package com.amit_kundu_io.auth.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amit_kundu_io.auth.R
import com.amit_kundu_io.auth.login.LoginActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val name = findViewById<TextInputEditText>(R.id.etName)
        val email = findViewById<TextInputEditText>(R.id.etEmail)
        val password = findViewById<TextInputEditText>(R.id.etPassword)

        val registerButton = findViewById<MaterialButton>(R.id.btnRegister)
        val loginText = findViewById<TextView>(R.id.tvLogin)

        // Navigate to Login
        loginText.setOnClickListener {
            finish()
        }

        // Register button
        registerButton.setOnClickListener {

            val userName = name.text.toString().trim()
            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString()

            when {
                userName.isEmpty() -> {
                    name.error = "Enter your name"
                }

                userEmail.isEmpty() -> {
                    email.error = "Enter your email"
                }

                !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches() -> {
                    email.error = "Enter a valid email"
                }

                userPassword.isEmpty() -> {
                    password.error = "Enter your password"
                }

                userPassword.length < 6 -> {
                    password.error = "Password must be at least 6 characters"
                }

                else -> {
                    Toast.makeText(
                        this,
                        "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Return to Login screen
                    finish()
                }
            }
        }
    }
}