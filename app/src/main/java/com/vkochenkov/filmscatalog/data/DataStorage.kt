package com.vkochenkov.filmscatalog.data

import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.Film

object DataStorage {
    var filmsArr: MutableList<Film> = arrayListOf(
        Film(R.string.film_title_1, R.string.film_description_1, R.drawable.im_film_1),
        Film(R.string.film_title_2, R.string.film_description_2, R.drawable.im_film_2),
        Film(R.string.film_title_3, R.string.film_description_3, R.drawable.im_film_3),
        Film(R.string.film_title_4, R.string.film_description_4, R.drawable.im_film_4),
        Film(R.string.film_title_5, R.string.film_description_5, R.drawable.im_film_5),
        Film(R.string.film_title_6, R.string.film_description_6, R.drawable.im_film_6)
    )

    var favouriteFilmsList: MutableList<Film> = ArrayList()

    var currentSelectedFilm: Film? = null
    var previousSelectedFilm: Film? = null
}