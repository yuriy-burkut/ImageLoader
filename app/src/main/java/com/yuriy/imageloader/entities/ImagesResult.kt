package com.yuriy.imageloader.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImagesResult (
    val media : List<Media>,
    val title : String
)

