package com.yuriy.imageloader.di.modules

import android.content.Context
import com.yuriy.imageloader.database.SavedImagesDao
import com.yuriy.imageloader.file_storage.FileStorageManager
import com.yuriy.imageloader.network.api.TenorApiService
import com.yuriy.imageloader.repository.ImagesRepository
import com.yuriy.imageloader.utils.MessageUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        apiService: TenorApiService,
        messageUtils: MessageUtils,
        dao: SavedImagesDao,
        fileStorage: FileStorageManager
    ): ImagesRepository {
        return ImagesRepository(apiService, messageUtils, dao, fileStorage)
    }

    @Provides
    @Singleton
    fun provideMessageUtils(context: Context): MessageUtils {
        return MessageUtils(context)
    }
}