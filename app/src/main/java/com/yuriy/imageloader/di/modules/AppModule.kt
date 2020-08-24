package com.yuriy.imageloader.di.modules

import com.yuriy.imageloader.network.NetHelper
import com.yuriy.imageloader.network.api.TenorApiClient
import com.yuriy.imageloader.network.api.TenorApiService
import com.yuriy.imageloader.repository.ImagesRepository
import com.yuriy.imageloader.repository.NetDataSource
import com.yuriy.imageloader.repository.NetDataSourceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        apiService: TenorApiService,
        netHelper: NetHelper
    ): ImagesRepository {
        return ImagesRepository(apiService, netHelper)
    }
}