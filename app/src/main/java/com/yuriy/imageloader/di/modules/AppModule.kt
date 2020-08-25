package com.yuriy.imageloader.di.modules

import android.content.Context
import com.yuriy.imageloader.network.NetHelper
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
        context: Context,
        apiService: TenorApiService,
        netHelper: NetHelper,
        messageUtils: MessageUtils
    ): ImagesRepository {
        return ImagesRepository(context, apiService, netHelper, messageUtils)
    }

    @Provides
    @Singleton
    fun provideMessageUtils(context: Context): MessageUtils {
        return MessageUtils(context)
    }
}