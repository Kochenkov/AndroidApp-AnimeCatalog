package com.vkochenkov.filmscatalog.di.module

import com.vkochenkov.filmscatalog.model.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(): Repository {
        return Repository()
    }
}