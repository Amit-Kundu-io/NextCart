/**
 * File: LoginActivity.kt
 * Created by Amit Kundu
 * Created on 04-Nov-26
 */

package com.amit_kundu_io.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity


import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.amit_kundu_io.auth.R
import com.amit_kundu_io.auth.forgotpassword.ForgotPasswordActivity
import com.amit_kundu_io.auth.register.RegisterActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlin.jvm.java


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<TextInputEditText>(R.id.etEmail)
        val password = findViewById<TextInputEditText>(R.id.etPassword)
        val loginButton = findViewById<MaterialButton>(R.id.btnLogin)

        val registerText = findViewById<TextView>(R.id.tvRegister)
        val forgotPasswordText = findViewById<TextView>(R.id.tvForgotPassword)

        // Register navigation
        registerText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Forgot password navigation
        forgotPasswordText.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        // Login
        loginButton.setOnClickListener {
            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString()

            if (userEmail.isEmpty()) {
                email.error = "Enter email"
            }
            else if (userPassword.isEmpty()) {
                password.error = "Enter password"
            }
            else if (
                userEmail.isNotEmpty() && userPassword.isNotEmpty()
            ) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}