package com.yuriy.imageloader.network.api

import retrofit2.Retrofit

class TenorApiClient(retrofit: Retrofit) {

    val apiService: TenorApiService = retrofit.create(TenorApiService::class.java)

}