package com.yuriy.imageloader.di.components

import android.content.Context
import com.yuriy.imageloader.di.modules.AppModule
import com.yuriy.imageloader.di.modules.ViewModelsModule
import com.yuriy.imageloader.di.modules.LocalStorageModule
import com.yuriy.imageloader.di.modules.NetworkModule
import com.yuriy.imageloader.ui.activities.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelsModule::class, NetworkModule::class, LocalStorageModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)

}