package com.volcanolabs.githuborganizations.data.models

import com.google.gson.annotations.SerializedName

data class GithubAccessToken(
    @SerializedName("access_token")
    var accessToken: String,
    @SerializedName("token_type")
    var tokenType: String,
    @SerializedName("scope")
    var scope: String
)
