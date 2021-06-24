package com.vkochenkov.filmscatalog.di.module

import android.app.Application
import com.vkochenkov.filmscatalog.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideAppContext(): Application = app
}