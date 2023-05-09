package com.hakanbayazithabes.androidkotlin.retrofitService

import com.hakanbayazithabes.androidkotlin.models.UserSignUp
import retrofit2.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitLoginService {
    @POST("api/user/signup")
    suspend fun signUp(@Body userSignUp:UserSignUp, @Header("Authrization") authorization:String):
            Response<ResponseBody>
}