package com.yuriy.imageloader.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Gif(
    @Json(name = "preview") val previewUrl: String,
    @Json(name = "url") val imageUrl: String

)