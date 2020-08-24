package com.yuriy.imageloader.di.modules

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yuriy.imageloader.network.NetHelper
import com.yuriy.imageloader.network.api.TenorApiClient
import com.yuriy.imageloader.network.api.TenorApiService
import com.yuriy.imageloader.network.interceptors.AuthInterceptor
import com.yuriy.imageloader.network.interceptors.ConnectivityInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): TenorApiService {
        return TenorApiClient(retrofit).apiService
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.tenor.com/v1/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context, netHelper: NetHelper): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .protocols(listOf(Protocol.HTTP_1_1))
            .addInterceptor(ConnectivityInterceptor(netHelper))
            .addInterceptor(AuthInterceptor())
            .addInterceptor(ChuckInterceptor(context))
            .build()
    }

    @Provides
    @Singleton
    fun provideNetHelper(context: Context) = NetHelper(context)
}