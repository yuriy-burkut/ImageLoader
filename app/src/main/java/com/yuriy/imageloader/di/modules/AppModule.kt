package com.yuriy.imageloader.di.modules

import com.yuriy.imageloader.repositories.ImagesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRepository(): ImagesRepository {
        return ImagesRepository()
    }
}