package com.vkochenkov.filmscatalog.presentation.view.recycler

import com.vkochenkov.filmscatalog.data.Film

interface FilmItemClickListener {

    fun detailsClickListener(film: Film)

    fun likeClickListener(film: Film, position: Int)
}