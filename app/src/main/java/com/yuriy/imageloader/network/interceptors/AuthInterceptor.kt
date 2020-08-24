package com.yuriy.imageloader.network.interceptors

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val newUrl: HttpUrl =
            chain.request().url().newBuilder().addQueryParameter("key", "5K4475R41AF8").build()
        val request: Request = chain.request().newBuilder().url(newUrl).build()

        return chain.proceed(request)
    }
}