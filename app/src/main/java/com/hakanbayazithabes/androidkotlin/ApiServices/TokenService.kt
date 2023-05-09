package com.hakanbayazithabes.androidkotlin.ApiServices

import android.os.Build
import com.hakanbayazithabes.androidkotlin.BuildConfig
import com.hakanbayazithabes.androidkotlin.consts.ApiConsts
import com.hakanbayazithabes.androidkotlin.models.ApiResponse
import com.hakanbayazithabes.androidkotlin.models.Token
import com.hakanbayazithabes.androidkotlin.retrofitService.ApiClient
import com.hakanbayazithabes.androidkotlin.retrofitService.RetrofitTokenService

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
    }
}