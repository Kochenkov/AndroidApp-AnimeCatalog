package com.vkochenkov.filmscatalog.viewmodel

import androidx.lifecycle.ViewModel
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.model.Repository

abstract class CommonViewModel: ViewModel() {

    var repository: Repository = App.instance!!.repository

    fun likeFilm(name: String) {
        repository.likeFilm(name)
    }

    fun unlikeFilm(name: String) {
        repository.unlikeFilm(name)
    }
}