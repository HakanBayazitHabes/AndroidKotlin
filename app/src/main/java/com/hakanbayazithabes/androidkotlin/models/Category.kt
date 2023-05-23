package com.hakanbayazithabes.androidkotlin.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

import com.google.gson.annotations.SerializedName

@Parcelize
data class Category(
    @SerializedName("Id") var Id: Int,
    @SerializedName("Name") var Name: String
) : Parcelable {
    override fun toString(): String {
        return Name
    }
}