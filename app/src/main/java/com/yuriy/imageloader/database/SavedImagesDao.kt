package com.yuriy.imageloader.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yuriy.imageloader.entities.IMAGES_TABLE_NAME
import com.yuriy.imageloader.entities.SavedImageInfo


@Dao
interface SavedImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageInfo: SavedImageInfo)

    @Query("select * from $IMAGES_TABLE_NAME")
    fun getImagesFactory(): DataSource.Factory<Int, SavedImageInfo>

}