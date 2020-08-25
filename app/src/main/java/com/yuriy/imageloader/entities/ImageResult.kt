package com.yuriy.imageloader.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageResult (
    val media : List<Media>,
    val title : String,
    val id: String
)

