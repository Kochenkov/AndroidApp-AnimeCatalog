package com.vkochenkov.filmscatalog

import android.app.Application
import com.vkochenkov.filmscatalog.di.component.AppComponent
import com.vkochenkov.filmscatalog.di.component.DaggerAppComponent
import com.vkochenkov.filmscatalog.di.module.*

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .databaseModule(DatabaseModule())
            .repositoryModule(RepositoryModule())
            .build()
    }
}