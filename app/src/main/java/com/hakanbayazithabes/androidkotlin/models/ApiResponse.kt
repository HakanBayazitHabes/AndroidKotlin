package com.hakanbayazithabes.androidkotlin.models

data class ApiResponse<T>(var isSuccessful:Boolean,var success:T?=null, var fail:ApiError?=null)