package com.hakanbayazithabes.androidkotlin.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("Id") var Id: Int,
    @SerializedName("Name") var Name: String,
    @SerializedName("Price") var Price: Double,
    @SerializedName("Color") var Color: String,
    @SerializedName("Stock") var Stock: Int,
    @SerializedName("PhotoPath") var PhotoPath: String,
    @SerializedName("Category_Id") var Category_Id: Int,
    @SerializedName("Category") var Category: Category?
) : Parcelable