package com.yuriy.imageloader.network.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yuriy.imageloader.entities.ImagesResult

@JsonClass(generateAdapter = true)
data class TenorGifSearchResponse(
    @Json(name = "weburl") val webUrl: String,
    val imagesResults: List<ImagesResult>?,
    val next: Int
)