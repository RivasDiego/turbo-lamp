package com.rivas.dummydictionary.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.rivas.dummydictionary.DummyDictionaryApplication
import com.rivas.dummydictionary.MainActivity
import com.rivas.dummydictionary.R
import com.rivas.dummydictionary.databinding.ActivityLoginBinding
import com.rivas.dummydictionary.ui.ViewModelFactory
import java.util.*
import kotlin.concurrent.schedule


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    val app by lazy {
        application as DummyDictionaryApplication
    }
    private val viewModelFactory by lazy {
        ViewModelFactory(app.getLoginRepository())
    }
    private val viewModel: LoginViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (app.isUserLogin()) {
            return startMainActivity()
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel

        viewModel.status.observe(this){ status ->
            handleUiState(status)
        }
    }

    private fun handleUiState(status: LoginUiStatus) {
        when (status) {
            is LoginUiStatus.Error -> {
                binding.loading.visibility = View.INVISIBLE
                Log.d("Login list status", "Error")
                showAlert("Something went wrong")
            }
            is LoginUiStatus.ErrorWithMessage -> {
                Timer().schedule(2000) {
                    binding.loading.visibility = View.INVISIBLE
                    showAlert("Oops ${status.message}")
                }
            }
            LoginUiStatus.Loading -> {
                binding.loading.visibility = View.VISIBLE
                Log.d("Login List Status", "Loading")
            }
            LoginUiStatus.Resume -> Log.d("Login List Status","Resume")
            is LoginUiStatus.Success -> {
                binding.loading.visibility = View.INVISIBLE
                app.saveAuthToken(status.token)
                startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showAlert(message: String) {
        Snackbar
            .make(binding.constraint, message, Snackbar.LENGTH_LONG)
            .show()
    }
}