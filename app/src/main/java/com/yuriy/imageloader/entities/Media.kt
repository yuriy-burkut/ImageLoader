package com.yuriy.imageloader.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Media (val gif: Gif)