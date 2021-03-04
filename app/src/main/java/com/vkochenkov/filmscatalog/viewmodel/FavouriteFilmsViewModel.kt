package com.vkochenkov.filmscatalog.viewmodel

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.model.LocalDataStore.currentFavouritesPageSize
import com.vkochenkov.filmscatalog.model.LocalDataStore.currentFilmsPageSize
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.model.Repository

class FavouriteFilmsViewModel : ViewModel() {

    private var repository: Repository = App.instance!!.repository

    private var mutableFavouritesLiveData = MutableLiveData<List<Film>>()

    val favouritesLiveData: LiveData<List<Film>>
        get()  = mutableFavouritesLiveData

    fun getFavouritesWithPagging(progressBar: ProgressBar) {
        //а смысл?
        progressBar.visibility = View.VISIBLE
        mutableFavouritesLiveData.postValue(repository.getFavouritesWithPagination(currentFavouritesPageSize))
        currentFavouritesPageSize += 10

        progressBar.visibility = View.INVISIBLE
    }

    fun likeFilm(name: String) {
        repository.likeFilm(name)
    }

    fun unlikeFilm(name: String) {
        repository.unlikeFilm(name)
    }
}