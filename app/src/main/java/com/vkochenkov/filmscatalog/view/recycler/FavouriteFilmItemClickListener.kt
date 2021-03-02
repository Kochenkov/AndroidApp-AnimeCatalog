package com.vkochenkov.filmscatalog.view.recycler

import com.vkochenkov.filmscatalog.model.Film

interface FavouriteFilmItemClickListener {

    fun detailsClickListener(film: Film)

    fun deleteClickListener(film: Film, position: Int)
}