package com.volcanolabs.githuborganizations

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import com.volcanolabs.githuborganizations.presentation.ui.login.LoginActivity
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GithubOrganizationsApp : Application()