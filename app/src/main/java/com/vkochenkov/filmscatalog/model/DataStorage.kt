package com.vkochenkov.filmscatalog.model

import com.vkochenkov.filmscatalog.model.entities.Film

//пока этот класс вместо базы данных
object DataStorage {
    var favouriteFilmsList: MutableList<Film> = ArrayList()
    var currentSelectedFilm: Film? = null
    var previousSelectedFilm: Film? = null
}