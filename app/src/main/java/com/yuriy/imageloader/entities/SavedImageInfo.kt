package com.yuriy.imageloader.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val IMAGES_TABLE_NAME: String = "saved_images"

@Entity(tableName = IMAGES_TABLE_NAME)
data class SavedImageInfo(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val preview_url: String,
    val image_url: String
)