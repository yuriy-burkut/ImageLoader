package com.yuriy.imageloader.network.api

import com.yuriy.imageloader.network.responses.TenorGifSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TenorApiService {

    @GET("search")
    suspend fun getImages(
        @Query("q") searchRequest: String,
        @Query("limit") limit: Int,
        @Query("pos") position: Int? = null
    ): TenorGifSearchResponse?

    @GET("search_suggestions")
    suspend fun getSearchSuggestions(
        @Query("q") searchRequest: String, @Query("limit") limit: Int
    )

}