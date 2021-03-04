package com.vkochenkov.filmscatalog.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FilmsDao {

    @Query("SELECT * FROM Films")
    fun getAllFilms() : LiveData<List<Film>>

    @Query("SELECT * FROM Films WHERE serverName = :name")
    fun getFilmByName(name: String) : Film

    @Query("SELECT * FROM Films WHERE isLiked = 1")
    fun getLikedFilms() : LiveData<List<Film>>

    @Query("UPDATE Films SET isLiked = 1 WHERE serverName = :name")
    fun setLikedFilm(name: String)

    @Query("UPDATE Films SET isLiked = 0 WHERE serverName = :name")
    fun setUnlikedFilm(name: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllFilms(countryList: List<Film>)

    @Query("DELETE FROM Films")
    fun deleteAllFilms()

    @Query("SELECT * FROM Films LIMIT :pages+10")
    fun getFilmsWithPagination(pages: Int): List<Film>
}