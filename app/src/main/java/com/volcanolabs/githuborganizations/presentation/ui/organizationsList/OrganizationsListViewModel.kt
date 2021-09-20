package com.volcanolabs.githuborganizations.presentation.ui.organizationsList

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volcanolabs.githuborganizations.data.models.Organization
import com.volcanolabs.githuborganizations.domain.interactors.GetOrganizations
import com.volcanolabs.githuborganizations.presentation.ui.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrganizationsListViewModel @Inject constructor(
    private val getOrganizations: GetOrganizations,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _organizations: MutableLiveData<List<Organization>> = MutableLiveData()
    val organizations: LiveData<List<Organization>> = _organizations
    private val _screenStatus: MutableLiveData<SCREEN_STATUS> = MutableLiveData()
    val screenStatus: LiveData<SCREEN_STATUS> = _screenStatus

    init {
        _screenStatus.value = SCREEN_STATUS.LOADING_DATA
        viewModelScope.launch {
            val organizations = getOrganizations.execute(100)
            _organizations.value = organizations
            _screenStatus.value = SCREEN_STATUS.DEFAULT
        }
    }

    fun performLogout() {
        sharedPreferences.edit().clear().apply()
        _screenStatus.value = SCREEN_STATUS.LOGGED_OUT
    }

    enum class SCREEN_STATUS {
        DEFAULT,
        LOADING_DATA,
        LOGGED_OUT
    }
}