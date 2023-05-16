package com.hakanbayazithabes.androidkotlin.models

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("url") var url: String
) {
}