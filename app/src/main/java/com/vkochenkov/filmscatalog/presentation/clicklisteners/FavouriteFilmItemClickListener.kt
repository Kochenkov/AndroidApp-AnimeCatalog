package com.vkochenkov.filmscatalog.presentation.clicklisteners

import com.vkochenkov.filmscatalog.model.db.Film

interface FavouriteFilmItemClickListener {

    fun detailsClickListener(film: Film)

    fun deleteClickListener(film: Film, position: Int)
}