package com.vkochenkov.filmscatalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vkochenkov.filmscatalog.model.db.Film

class FavouriteFilmsViewModel : CommonViewModel() {

    private var mutableFavouritesLiveData = MutableLiveData<List<Film>>()

    val favouritesLiveData: LiveData<List<Film>>
        get() = mutableFavouritesLiveData

    fun getFavourites() {
        mutableFavouritesLiveData.postValue(repository.getFavourites())
    }
}