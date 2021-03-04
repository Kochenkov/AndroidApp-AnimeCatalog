package com.vkochenkov.filmscatalog.model

import com.vkochenkov.filmscatalog.model.db.Film

object LocalDataStore {
    /**
     * contains current selected film for change title color
     */
    var currentSelectedFilm: Film? = null

    /**
     * uses for pagging
     */
    var currentFilmsPageSize = 0
    var currentFavouritesPageSize = 0
}