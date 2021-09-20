package com.volcanolabs.githuborganizations.data.models

import com.google.gson.annotations.SerializedName

data class Organization(
    @SerializedName("login") var login: String?,
    @SerializedName("id") var id: Int,
    @SerializedName("node_id") var nodeId: String,
    @SerializedName("url") var url: String?,
    @SerializedName("repos_url") var reposUrl: String?,
    @SerializedName("events_url") var eventsUrl: String?,
    @SerializedName("hooks_url") var hooksUrl: String?,
    @SerializedName("issues_url") var issuesUrl: String?,
    @SerializedName("members_url") var membersUrl: String?,
    @SerializedName("public_members_url") var publicMembersUrl: String?,
    @SerializedName("avatar_url") var avatarUrl: String?,
    @SerializedName("description") var description: String?
)