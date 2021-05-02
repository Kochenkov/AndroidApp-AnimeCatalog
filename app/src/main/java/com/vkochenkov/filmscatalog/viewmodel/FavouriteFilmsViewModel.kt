package com.vkochenkov.filmscatalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vkochenkov.filmscatalog.model.Repository
import com.vkochenkov.filmscatalog.model.db.Film
import javax.inject.Inject

class FavouriteFilmsViewModel @Inject constructor(override val repository: Repository) : CommonViewModel(repository) {

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
}