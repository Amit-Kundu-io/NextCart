
package com.amit_kundu_io.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amit_kundu_io.auth.R
import com.amit_kundu_io.auth.forgotpassword.ForgotPasswordActivity
import com.amit_kundu_io.auth.register.RegisterActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity (

): AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var progressBar: ProgressBar
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeViews()
        setupListeners()
        observeUiState()
    }

    private fun initializeViews() {
        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)

        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
    }

    private fun setupListeners() {

        findViewById<TextView>(R.id.tvRegister).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        findViewById<TextView>(R.id.tvForgotPassword).setOnClickListener {
            startActivity(
                Intent(this, ForgotPasswordActivity::class.java)
            )
        }

        loginButton.setOnClickListener {
            viewModel.login(
                email = email.text?.toString()?.trim().orEmpty(),
                password = password.text?.toString().orEmpty()
            )
        }
    }

    private fun observeUiState() {
        viewModel.uiState.observe(this) { state ->

            // Loading
            progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

            loginButton.isEnabled = !state.isLoading

            // Validation errors
            tilEmail.error = state.emailError
            tilPassword.error = state.passwordError

            // General error
            state.errorMessage?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }

            // Login success
            if (state.isSuccess) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            }
        }
    }
}
