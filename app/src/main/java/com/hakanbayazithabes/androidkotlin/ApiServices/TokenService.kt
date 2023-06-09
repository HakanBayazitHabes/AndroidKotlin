package com.hakanbayazithabes.androidkotlin.ApiServices

import android.content.Context
import androidx.appcompat.widget.ThemedSpinnerAdapter.Helper
import com.google.gson.Gson
import com.hakanbayazithabes.androidkotlin.BuildConfig
import com.hakanbayazithabes.androidkotlin.consts.ApiConsts
import com.hakanbayazithabes.androidkotlin.models.ApiResponse
import com.hakanbayazithabes.androidkotlin.models.Introspec
import com.hakanbayazithabes.androidkotlin.models.TokenAPI
import com.hakanbayazithabes.androidkotlin.retrofitService.ApiClient
import com.hakanbayazithabes.androidkotlin.retrofitService.RetrofitTokenService
import com.hakanbayazithabes.androidkotlin.utility.GlobalApp
import com.hakanbayazithabes.androidkotlin.utility.HelperService
import okhttp3.Credentials

class TokenService {
    companion object {
        private var retrofitTokenServiceWithoutInterceptor =
            ApiClient.builderService(ApiConsts.authBaseUrl, RetrofitTokenService::class.java, false)

        suspend fun getTokenWithClientCredentials(): ApiResponse<TokenAPI> {

            try {
                var response = retrofitTokenServiceWithoutInterceptor.getTokenWithClientCredentials(
                    BuildConfig.ClientId_CC,
                    BuildConfig.Client_Secret_CC,
                    ApiConsts.clientCredentialGrantType
                )

                //Helper method yazılacak başarız durumlar için
                if (!response.isSuccessful) return ApiResponse(false)

                return ApiResponse(true, response.body() as TokenAPI)
            }
            catch (ex: Exception) {
                return HelperService.handleException(ex)
            }

        }

        fun refreshToken(refreshToken: String): ApiResponse<TokenAPI> {

            try {
                var response = retrofitTokenServiceWithoutInterceptor.refreshToken(
                    BuildConfig.ClientId_ROP,
                    BuildConfig.Client_Secret_ROP,
                    ApiConsts.refreshTokenCredentialGrantType,
                    refreshToken
                ).execute()

                return if (response.isSuccessful) {
                    ApiResponse(true, response.body() as TokenAPI)
                } else {
                    ApiResponse(false, response.body() as TokenAPI)
                }
            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }

        }

        suspend fun checkToken(): ApiResponse<Unit> {

            try {
                var preference =
                    GlobalApp.getContext().getSharedPreferences("token_api", Context.MODE_PRIVATE)

                var tokenString: String? =
                    preference.getString("token", null) ?: return ApiResponse(false);


                var token: TokenAPI = Gson().fromJson(tokenString, TokenAPI::class.java)

                var authorization: String =
                    okhttp3.Credentials.basic("resource_product_api", "apisecret")


                var response =
                    retrofitTokenServiceWithoutInterceptor.checkToken(
                        token.access_token,
                        authorization
                    )

                if (!response.isSuccessful) return ApiResponse(false)


                var introspec = response.body() as Introspec


                if (!introspec.Active) return ApiResponse(false)


                return ApiResponse(true)
            } catch (ex: java.lang.Exception) {
                return HelperService.handleException(ex)
            }


        }
    }
}