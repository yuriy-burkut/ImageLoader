package com.yuriy.imageloader.network.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yuriy.imageloader.entities.ImageResult

@JsonClass(generateAdapter = true)
data class TenorGifSearchResponse(
    @Json(name = "weburl")
    val webUrl: String,
    @Json(name = "results")
    val imageResults: List<ImageResult>?,
    val next: Int
)