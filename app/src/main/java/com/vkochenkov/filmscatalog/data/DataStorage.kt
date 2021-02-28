package com.vkochenkov.filmscatalog.data

//пока этот класс вместо базы данных
object DataStorage {
    var favouriteFilmsList: MutableList<Film> = ArrayList()
    var currentSelectedFilm: Film? = null
    var previousSelectedFilm: Film? = null
}