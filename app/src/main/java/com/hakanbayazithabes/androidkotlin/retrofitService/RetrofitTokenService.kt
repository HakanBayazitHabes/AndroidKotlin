package com.hakanbayazithabes.androidkotlin.retrofitService

import android.media.session.MediaSession
import com.hakanbayazithabes.androidkotlin.models.Introspec
import com.hakanbayazithabes.androidkotlin.models.Token
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitTokenService {

    @FormUrlEncoded
    @POST("connect/token")
    suspend fun getTokenWithClientCredentials(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String
    ): Response<Token>


    @FormUrlEncoded
    @POST("connect/token")
    suspend fun checkToken(
        @Field("token") token: String,
        @Header("Authorization") authorization: String
    ) : Response<Introspec>


    @FormUrlEncoded
    @POST("connect/token")
    fun refreshToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
        @Field("refresh_token") refreshToken: String
    ): Call<Token>
}