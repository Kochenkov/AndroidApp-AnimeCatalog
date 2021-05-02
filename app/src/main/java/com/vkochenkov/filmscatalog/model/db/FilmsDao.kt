package com.vkochenkov.filmscatalog.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FilmsDao {

    @Query("SELECT * FROM Films WHERE isLiked = 1")
    fun getFavourites() : List<Film>

    @Query("SELECT * FROM Films WHERE serverName = :name")
    fun getFilm(name: String): Film

    @Query("UPDATE Films SET isLiked = 1 WHERE serverName = :name")
    fun setLikeFilm(name: String)

    @Query("UPDATE Films SET isLiked = 0 WHERE serverName = :name")
    fun setUnlikeFilm(name: String)

    @Query("UPDATE Films SET notificationDate = :date WHERE serverName = :name")
    fun setNotificationFilm(name: String, date: Long)

    @Query("UPDATE Films SET notificationDate = 0 WHERE serverName = :name")
    fun clearNotificationFilm(name: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllFilms(countryList: List<Film>)

    @Query("SELECT * FROM Films LIMIT :pages+10")
    fun getFilmsWithPagination(pages: Int): List<Film>
}