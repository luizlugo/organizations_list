package com.volcanolabs.githuborganizations.presentation.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.volcanolabs.githuborganizations.Constants.GITHUB_AUTH_URL
import com.volcanolabs.githuborganizations.R
import com.volcanolabs.githuborganizations.databinding.ActivityLoginBinding
import com.volcanolabs.githuborganizations.presentation.ui.organizationsList.OrganizationsList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        binding.btnLoginWithGithub.setOnClickListener {
            val browserIntent =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(GITHUB_AUTH_URL)
                )
            startActivity(browserIntent)
        }
        viewModel.screenStatus.observe(this, {
            when (it) {
                LoginViewModel.SCREEN_STATUS.LOADING -> showLoadingState()
                LoginViewModel.SCREEN_STATUS.LOGIN_SUCCESSFUL -> loginSuccessfully()
                LoginViewModel.SCREEN_STATUS.LOGIN_ERROR -> showLoginError()
                else -> resetUI()
            }
        })
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()

        val uri = intent.data

        if (uri != null) {
            if (!uri.getQueryParameter("code").isNullOrEmpty()) {
                viewModel.getAccessToken(uri.getQueryParameter("code") ?: "")
            } else if (!uri.getQueryParameter("error").isNullOrEmpty()) {
                viewModel.loginErrorStatus(uri.getQueryParameter("error_description") ?: "")
            }
        }
    }

    private fun resetUI() {
        binding.btnLoginWithGithub.isEnabled = true
    }

    private fun showLoadingState() {
        binding.btnLoginWithGithub.isEnabled = false
    }

    private fun loginSuccessfully() {
        val mainActivityIntent = Intent(this, OrganizationsList::class.java)
        mainActivityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(mainActivityIntent)
    }

    private fun showLoginError() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.login)
            .setMessage(viewModel.getLoginError())
            .setPositiveButton(R.string.ok_option) { dialog, which ->
                viewModel.resetScreenStatus()
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}