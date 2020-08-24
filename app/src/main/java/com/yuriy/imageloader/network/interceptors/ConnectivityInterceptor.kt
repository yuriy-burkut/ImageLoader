package com.yuriy.imageloader.network.interceptors

import com.yuriy.imageloader.network.NetHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(private val netHelper: NetHelper) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!netHelper.isOnline()) {
            throw IOException("No internet connection")
        }
        return chain.proceed(chain.request())
    }
}