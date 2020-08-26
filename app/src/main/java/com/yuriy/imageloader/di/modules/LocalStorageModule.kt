package com.yuriy.imageloader.di.modules

import android.content.Context
import androidx.room.Room
import com.yuriy.imageloader.database.DATABASE_NAME
import com.yuriy.imageloader.database.SavedImagesDao
import com.yuriy.imageloader.database.SavedImagesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalStorageModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): SavedImagesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            SavedImagesDatabase::class.java, DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(database: SavedImagesDatabase): SavedImagesDao {
        return database.getImagesDao()
    }

}