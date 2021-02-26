package com.vkochenkov.filmscatalog.presentation.view.recycler

import com.vkochenkov.filmscatalog.data.Film

interface FavouriteFilmItemClickListener {

    fun detailsClickListener(film: Film)

    fun deleteClickListener(film: Film, position: Int)
}