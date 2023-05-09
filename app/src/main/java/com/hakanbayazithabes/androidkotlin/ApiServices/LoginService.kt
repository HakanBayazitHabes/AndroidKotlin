package com.hakanbayazithabes.androidkotlin.ApiServices

import com.hakanbayazithabes.androidkotlin.consts.ApiConsts
import com.hakanbayazithabes.androidkotlin.models.ApiResponse
import com.hakanbayazithabes.androidkotlin.models.Token
import com.hakanbayazithabes.androidkotlin.models.UserSignUp
import com.hakanbayazithabes.androidkotlin.retrofitService.ApiClient
import com.hakanbayazithabes.androidkotlin.retrofitService.RetrofitLoginService
import com.hakanbayazithabes.androidkotlin.retrofitService.RetrofitTokenService

class LoginService {
    companion object {
        private var retrofitTokenServiceWithoutInterceptor =
            ApiClient.builderService(ApiConsts.authBaseUrl, RetrofitLoginService::class.java, false)

        suspend fun signUp(userSignUp: UserSignUp): ApiResponse<Unit> {
            var tokenResponse = TokenService.getTokenWithClientCredentials()

            if (!tokenResponse.isSuccessful) return ApiResponse(false)

            var token = tokenResponse.success!!

            var signUpResponse = retrofitTokenServiceWithoutInterceptor.signUp(
                userSignUp,
                "bearer ${token.accessToken}"
            )

            if (!signUpResponse.isSuccessful) return ApiResponse(false)

            return ApiResponse(true)

        }
    }
}