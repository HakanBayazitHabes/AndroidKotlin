package com.hakanbayazithabes.androidkotlin.ApiServices

import androidx.appcompat.widget.ThemedSpinnerAdapter.Helper
import com.hakanbayazithabes.androidkotlin.BuildConfig
import com.hakanbayazithabes.androidkotlin.consts.ApiConsts
import com.hakanbayazithabes.androidkotlin.models.ApiResponse
import com.hakanbayazithabes.androidkotlin.models.TokenAPI
import com.hakanbayazithabes.androidkotlin.models.UserSignIn
import com.hakanbayazithabes.androidkotlin.models.UserSignUp
import com.hakanbayazithabes.androidkotlin.retrofitService.ApiClient
import com.hakanbayazithabes.androidkotlin.retrofitService.RetrofitLoginService
import com.hakanbayazithabes.androidkotlin.utility.HelperService

class LoginService {
    companion object {
        private var retrofitTokenServiceWithoutInterceptor =
            ApiClient.builderService(ApiConsts.authBaseUrl, RetrofitLoginService::class.java, false)

        suspend fun signUp(userSignUp: UserSignUp): ApiResponse<Unit> {

            try {
                var tokenResponse = TokenService.getTokenWithClientCredentials()

                if (!tokenResponse.isSuccessful) return ApiResponse(false)

                var token = tokenResponse.success!!

                var signUpResponse = retrofitTokenServiceWithoutInterceptor.signUp(
                    userSignUp,
                    "bearer ${token.accessToken}"
                )

                if (!signUpResponse.isSuccessful) return HelperService.handlerApiError(
                    signUpResponse
                )

                return ApiResponse(true)
            } catch (e: Exception) {
                return HelperService.handleException(e)
            }


        }

        suspend fun signIn(signIn: UserSignIn): ApiResponse<Unit> {

            try {
                var response = retrofitTokenServiceWithoutInterceptor.signIn(
                    BuildConfig.ClientId_ROP,
                    BuildConfig.Client_Secret_ROP,
                    ApiConsts.resourceOwnerPasswordCredentialGrantType,
                    signIn.Email,
                    signIn.Password
                )

                if (!response.isSuccessful) return HelperService.handlerApiError(response)

                var token = response.body() as TokenAPI

                HelperService.saveTokenSharedPreferences(token)

                return ApiResponse(false)
            } catch (e: Exception) {
                return HelperService.handleException(e)
            }


        }
    }
}