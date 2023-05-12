package com.hakanbayazithabes.androidkotlin.models

import com.google.gson.annotations.SerializedName

data class TokenAPI(
    @SerializedName("access_token") var access_token: String,
    @SerializedName("expires_in") var expires_in: Int,
    @SerializedName("token_type") var token_type: String,
    @SerializedName("refresh_token") var refresh_token: String,
    @SerializedName("scope") var scope: String
) {
}