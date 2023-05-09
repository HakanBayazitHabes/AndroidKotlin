package com.hakanbayazithabes.androidkotlin.ApiServices

import android.content.Context
import android.os.Build
import com.google.gson.Gson
import com.hakanbayazithabes.androidkotlin.BuildConfig
import com.hakanbayazithabes.androidkotlin.consts.ApiConsts
import com.hakanbayazithabes.androidkotlin.models.ApiResponse
import com.hakanbayazithabes.androidkotlin.models.Introspec
import com.hakanbayazithabes.androidkotlin.models.Token
import com.hakanbayazithabes.androidkotlin.retrofitService.ApiClient
import com.hakanbayazithabes.androidkotlin.retrofitService.RetrofitTokenService
import com.hakanbayazithabes.androidkotlin.utility.GlobalApp
import okhttp3.Credentials

class TokenService {
    companion object {
        private var retrofitTokenServiceWithoutInterceptor =
            ApiClient.builderService(ApiConsts.authBaseUrl, RetrofitTokenService::class.java, false)

        suspend fun getTokenWithClientCredentials(): ApiResponse<Token> {
            var response = retrofitTokenServiceWithoutInterceptor.getTokenWithClientCredentials(
                BuildConfig.ClientId_CC,
                BuildConfig.Client_Secret_CC,
                ApiConsts.clientCredentialGrantType
            )

            //Helper method yazılacak başarız durumlar için
            if (response.isSuccessful) return ApiResponse(false)

            return ApiResponse(true, response.body() as Token)
        }

        suspend fun refreshToken(refreshToken: String): ApiResponse<Token> {
            var response = retrofitTokenServiceWithoutInterceptor.refreshToken(
                BuildConfig.ClientId_ROP,
                BuildConfig.Client_Secret_ROP,
                ApiConsts.resourceOwnerPasswordCredentialGrantType,
                refreshToken
            ).execute()

            return if (response.isSuccessful) {
                ApiResponse(true, response.body() as Token)
            } else {
                ApiResponse(false, response.body() as Token)
            }

        }

        suspend fun checkToken() : ApiResponse<Unit>{
            var preferences = GlobalApp.getContext().getSharedPreferences("ApiToken", Context.MODE_PRIVATE)

            var tokenString : String? = preferences.getString("token",null) ?: return ApiResponse(false)

            var token:Token = Gson().fromJson(tokenString, Token::class.java)

            var authorization: String =
                Credentials.basic("resource_product_api","apisecret")

            var response = retrofitTokenServiceWithoutInterceptor.checkToken(token.accessToken, authorization)

            if (!response.isSuccessful) return ApiResponse(false)

            var introspec = response.body() as Introspec

            if (!introspec.Active) return ApiResponse(false)

            return ApiResponse(true)


        }
    }
}