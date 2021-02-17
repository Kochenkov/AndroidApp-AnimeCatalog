package com.vkochenkov.filmscatalog.recycler

import com.vkochenkov.filmscatalog.model.Film

interface FilmItemClickListener {

    fun detailsClickListener(film: Film)

    fun likeClickListener(film: Film, position: Int)
}