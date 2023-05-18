package com.hakanbayazithabes.androidkotlin.Interceptors

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.hakanbayazithabes.androidkotlin.ApiServices.TokenService
import com.hakanbayazithabes.androidkotlin.models.TokenAPI
import com.hakanbayazithabes.androidkotlin.ui.login.LoginActivity
import com.hakanbayazithabes.androidkotlin.utility.GlobalApp
import com.hakanbayazithabes.androidkotlin.utility.HelperService
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var token: TokenAPI? = null

        var request = chain.request()

        var preference =
            GlobalApp.getContext().getSharedPreferences("token_api", Context.MODE_PRIVATE)

        var tokenString = preference.getString("token", null)

        if (tokenString != null) {
            Log.i("token", "token Sharedpreference'den okundu")
            token = Gson().fromJson(tokenString, TokenAPI::class.java)

            request = request.newBuilder()
                .addHeader("Authorization", "Bearer " + token.access_token)
                .build()
        }
        var response = chain.proceed(request)

        if (response.code == 401) {
            Log.i("OkHttp", "AccessToken geçersiz 401 girdi")

            if (token != null) {
                var apiResponse = TokenService.refreshToken(token.refresh_token!!)

                if (apiResponse.isSuccessful) {
                    HelperService.saveTokenSharedPreferences(apiResponse.success!!)

                    var newToken = apiResponse.success

                    request = request.newBuilder()
                        .addHeader("Authorization", "Bearer " + newToken!!.access_token)
                        .build()

                } else {

                    var intent = Intent(GlobalApp.getContext(), LoginActivity::class.java)

                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                    GlobalApp.getContext().startActivity(intent)
                }
            }

        }
        return response
    }
}