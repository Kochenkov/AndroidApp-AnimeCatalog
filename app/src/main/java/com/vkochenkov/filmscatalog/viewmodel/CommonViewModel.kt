package com.vkochenkov.filmscatalog.viewmodel

import androidx.lifecycle.ViewModel
import com.vkochenkov.filmscatalog.model.Repository

abstract class CommonViewModel(open val repository: Repository) : ViewModel() {

    fun likeFilm(name: String) {
        repository.likeFilm(name)
    }

    fun unlikeFilm(name: String) {
        repository.unlikeFilm(name)
    }
}