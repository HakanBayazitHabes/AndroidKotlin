package com.hakanbayazithabes.androidkotlin.models

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("access_token") var accessToken: String,
    @SerializedName("refresh_token") var refreshToken: String,
    @SerializedName("expires_in") var expires: Int,
    @SerializedName("token_type") var tokenType: String,
    @SerializedName("scope") var scope: String
) {
}