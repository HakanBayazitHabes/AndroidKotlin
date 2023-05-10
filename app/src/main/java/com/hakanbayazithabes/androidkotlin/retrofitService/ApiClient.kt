package com.hakanbayazithabes.androidkotlin.retrofitService

import com.hakanbayazithabes.androidkotlin.Interceptors.NetworkInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class ApiClient {
    companion object {
        fun <T> builderService(
            baseUrl: String,
            retrofitService: Class<T>,
            existInterceptor: Boolean
        ): T {
            var clientBuilder = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(NetworkInterceptor());



            if (existInterceptor) {
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