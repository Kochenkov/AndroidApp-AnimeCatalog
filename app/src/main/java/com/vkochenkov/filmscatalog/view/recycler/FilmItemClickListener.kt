package com.vkochenkov.filmscatalog.view.recycler

import com.vkochenkov.filmscatalog.model.entities.Film

interface FilmItemClickListener {

    fun detailsClickListener(film: Film)

    fun likeClickListener(film: Film, position: Int)
}