package com.volcanolabs.githuborganizations.presentation.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volcanolabs.githuborganizations.Constants
import com.volcanolabs.githuborganizations.domain.interactors.GetAccessToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getAccessToken: GetAccessToken,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _screenStatus: MutableLiveData<SCREEN_STATUS> = MutableLiveData()
    val screenStatus: LiveData<SCREEN_STATUS> = _screenStatus
    private var errorDescription = ""

    fun getAccessToken(code: String) {
        viewModelScope.launch {
            _screenStatus.value = SCREEN_STATUS.LOADING
            val accessTokenData = getAccessToken.execute(code)

            if (accessTokenData.accessToken.isEmpty()) {
                _screenStatus.value = SCREEN_STATUS.LOGIN_ERROR
            } else {
                // Save token in local storage
                sharedPreferences.edit()
                    .putString(Constants.AUTH_TOKEN_KEY, accessTokenData.accessToken).apply()
                _screenStatus.value = SCREEN_STATUS.LOGIN_SUCCESSFUL
            }
        }
    }

    fun resetScreenStatus() {
        _screenStatus.value = SCREEN_STATUS.DEFAULT
    }

    fun loginErrorStatus(errorDescription: String) {
        this.errorDescription = errorDescription
        _screenStatus.value = SCREEN_STATUS.LOGIN_ERROR
    }

    fun getLoginError() : String {
        return errorDescription
    }

    enum class SCREEN_STATUS {
        DEFAULT,
        LOADING,
        LOGIN_ERROR,
        LOGIN_SUCCESSFUL
    }
}