package com.vkochenkov.filmscatalog.model

import com.vkochenkov.filmscatalog.model.db.Film

object LocalDataStore {
    var currentSelectedFilm: Film? = null
    var currentPageSize = 0
}