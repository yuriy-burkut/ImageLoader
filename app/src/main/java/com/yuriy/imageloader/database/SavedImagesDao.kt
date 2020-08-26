package com.yuriy.imageloader.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yuriy.imageloader.entities.IMAGES_TABLE_NAME
import com.yuriy.imageloader.entities.SavedImage


@Dao
interface SavedImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: SavedImage)

    @Query("select * from $IMAGES_TABLE_NAME")
    fun getImages(): LiveData<List<SavedImage>>

}