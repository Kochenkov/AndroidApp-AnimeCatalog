package com.vkochenkov.filmscatalog.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vkochenkov.filmscatalog.model.entities.Film

@Dao
interface FilmsDao {

    @Query("SELECT * FROM Films")
    fun getAllFilms() : LiveData<List<Film>>

    //todo
    @Query("SELECT * FROM Films WHERE serverName LIKE :name")
    fun getFilmByName(name: String) : Film

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFilms(countryList: List<Film>)

    @Query("DELETE FROM Films")
    fun deleteAllFilms()
}