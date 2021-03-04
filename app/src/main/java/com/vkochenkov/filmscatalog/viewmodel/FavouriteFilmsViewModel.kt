package com.vkochenkov.filmscatalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.model.Repository

class FavouriteFilmsViewModel : ViewModel() {

    private var repository: Repository = App.instance!!.repository

    val favouriteFilmsLiveData: LiveData<List<Film>>
        get()  = repository.getFavourites()

//    fun getFavourites(): LiveData<List<Film>>
//    {
//        return repository.getFavourites()
//    }

    fun likeFilm(name: String) {
        repository.likeFilm(name)
    }

    fun unlikeFilm(name: String) {
        repository.unlikeFilm(name)
    }
}