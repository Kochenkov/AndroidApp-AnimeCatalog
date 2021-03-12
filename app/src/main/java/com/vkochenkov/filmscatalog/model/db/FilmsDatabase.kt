package com.vkochenkov.filmscatalog.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(Film::class)], version = 2)
abstract class FilmsDatabase : RoomDatabase(){

    abstract fun filmsDao() : FilmsDao
}