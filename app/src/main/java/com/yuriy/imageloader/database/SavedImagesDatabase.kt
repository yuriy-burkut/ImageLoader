package com.yuriy.imageloader.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yuriy.imageloader.entities.SavedImage

const val DATABASE_NAME: String = "images.db"

@Database(entities = [SavedImage::class], version = 1, exportSchema = false)
abstract class SavedImagesDatabase() : RoomDatabase() {

    abstract fun getImagesDao(): SavedImagesDao

}