package com.vkochenkov.filmscatalog.di.module

import android.app.Application
import androidx.room.Room
import com.vkochenkov.filmscatalog.model.db.FilmsDao
import com.vkochenkov.filmscatalog.model.db.FilmsDatabase
import com.vkochenkov.filmscatalog.model.db.FilmsDatabase.Companion.dbName
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Application): FilmsDatabase {
        return Room.databaseBuilder(context, FilmsDatabase::class.java, dbName)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun providesDao(database: FilmsDatabase): FilmsDao {
        return database.filmsDao()
    }
}