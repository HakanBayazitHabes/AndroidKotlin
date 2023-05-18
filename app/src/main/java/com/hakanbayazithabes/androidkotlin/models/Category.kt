package com.hakanbayazithabes.androidkotlin.models

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("Id") var Id: Int,
    @SerializedName("Name") var Name: String
) {
    override fun toString(): String {
        return Name
    }
}