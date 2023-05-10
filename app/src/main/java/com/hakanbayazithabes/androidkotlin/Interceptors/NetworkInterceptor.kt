package com.hakanbayazithabes.androidkotlin.Interceptors

import com.hakanbayazithabes.androidkotlin.Exceptions.OfflineException
import com.hakanbayazithabes.androidkotlin.utility.LiveNetworkListener
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!LiveNetworkListener.isConencted()) {
            throw OfflineException("")
        }

        var request = chain.request()

        return chain.proceed(request)
    }

}