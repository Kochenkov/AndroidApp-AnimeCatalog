package com.vkochenkov.filmscatalog.view.recycler.main

import com.vkochenkov.filmscatalog.model.db.Film

interface FilmItemClickListener {

    fun detailsClickListener(film: Film)

    fun likeClickListener(film: Film, position: Int)
}