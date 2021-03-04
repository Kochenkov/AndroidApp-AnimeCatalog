package com.vkochenkov.filmscatalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.model.LocalDataStore.currentPageSize
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.model.Repository

class FilmsViewModel : ViewModel() {

    private var repository: Repository = App.instance!!.repository

  //  private var mutableFilmsLiveData = MutableLiveData<List<Film>>()

    val filmsLiveData: LiveData<List<Film>>
        get()  = repository.getFilmsFromDb()

    fun likeFilm(name: String) {
        repository.likeFilm(name)
    }

    fun unlikeFilm(name: String) {
        repository.unlikeFilm(name)
    }

//    fun getFilmsFromDb() {
//        mutableFilmsLiveData = repository.getFilmsFromDb(currentPageSize)
//    }

//    fun saveFilmsToDb(films: List<Film>) {
//        repository.saveFilmsToDb(films)
//    }
//
//    fun getFilmsFromApi(callback: Repository.GetFilmsFromApiCallback)
//    {
//        repository.getFilmsFromApi(callback)
//    }
}