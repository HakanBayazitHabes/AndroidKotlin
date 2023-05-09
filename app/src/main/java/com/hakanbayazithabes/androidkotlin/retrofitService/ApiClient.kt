package com.hakanbayazithabes.androidkotlin.retrofitService

import okhttp3.OkHttpClient
import retrofit2.Retrofit

class ApiClient {
    companion object{
        fun <T> builderService(baseUrl: String,retrofitService: Class<T>, existInterceptor: Boolean): T {
         var clientBuilder = OkHttpClient.Builder()
            if (existInterceptor){
                //interceptor eklenecek
            }
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(clientBuilder.build())
                .build()
                .create(retrofitService)
        }
    }
}