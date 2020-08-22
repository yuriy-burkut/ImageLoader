package com.yuriy.imageloader.application

import android.app.Application
import com.yuriy.imageloader.di.components.AppComponent
import com.yuriy.imageloader.di.components.DaggerAppComponent

class AppClass : Application() {

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}