package com.hakanbayazithabes.androidkotlin.retrofitService

import com.hakanbayazithabes.androidkotlin.models.TokenAPI
import com.hakanbayazithabes.androidkotlin.models.UserSignUp
import retrofit2.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitLoginService {

    @POST("api/user/signup")
    suspend fun signUp(
        @Body userSignUp: UserSignUp,
        @Header("Authorization") authorization: String
    ): Response<ResponseBody>


    @POST("connect/token")
    @FormUrlEncoded
    suspend fun signIn(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
        @Field("username") userName: String,
        @Field("password") password: String

    ):Response<TokenAPI>
}