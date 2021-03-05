package com.vkochenkov.filmscatalog.model

import com.vkochenkov.filmscatalog.model.db.Film

object LocalDataStore {
    /**
     * uses for change title color for selected film
     */
    var currentSelectedFilm: Film? = null

    /**
     * uses for pagination
     */
    var currentPageSize = 10

    /**
     * films fragment first start indicator
     */
    var isFirstStart = true
}