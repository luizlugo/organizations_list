package com.volcanolabs.githuborganizations

object Constants {
    const val AUTH_TOKEN_KEY = "mx.volcanolabs.githuborganizations.AUTH_TOKEN_KEY"
    const val GITHUB_CLIENT_ID = "[PASTE_HERE]"
    const val GITHUB_CLIENT_SECRET = "[PASTE_HERE]"
    const val GITHUB_AUTH_URL =
        "https://github.com/login/oauth/authorize?client_id=${GITHUB_CLIENT_ID}&scope=read:org"
}