package com.vkochenkov.filmscatalog.presentation.clicklisteners

import com.vkochenkov.filmscatalog.model.db.Film

interface FilmItemClickListener {

    fun detailsClickListener(film: Film)

    fun likeClickListener(film: Film, position: Int)
}