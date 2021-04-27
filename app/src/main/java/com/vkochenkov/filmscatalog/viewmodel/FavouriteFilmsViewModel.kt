package com.vkochenkov.filmscatalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.model.Repository
import com.vkochenkov.filmscatalog.model.db.Film

class FavouriteFilmsViewModel : ViewModel() {

    private var repository: Repository = App.instance!!.repository

    private var mutableFavouritesLiveData = MutableLiveData<List<Film>>()

    val favouritesLiveData: LiveData<List<Film>>
        get() = mutableFavouritesLiveData

    fun getFavourites() {
        repository.getFavourites(object : Repository.GetFilmsFromDatabaseCallback {
            override fun onSuccess(films: List<Film>) {
                mutableFavouritesLiveData.postValue(films)
            }
        })
    }

    fun likeFilm(name: String) {
        repository.likeFilm(name)
    }

    fun unlikeFilm(name: String) {
        repository.unlikeFilm(name)
    }
}