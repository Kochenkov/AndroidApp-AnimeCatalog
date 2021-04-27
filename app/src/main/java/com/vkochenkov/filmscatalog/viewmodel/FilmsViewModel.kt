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

    private var mutableFilmsLiveData = MutableLiveData<List<Film>>()
    private var mutableErrorLiveData = MutableLiveData<String>()

    val filmsLiveData: LiveData<List<Film>>
        get()  = mutableFilmsLiveData

    val errorLiveData: LiveData<String>
        get()  = mutableErrorLiveData

    fun getFilmsWithPaging(shouldIncrementPageCount: Boolean = true) {
        if (shouldIncrementPageCount) {
            currentPageSize += 10
        }
        getFilmsFromApi()
    }

    fun getFilmsWithoutPaging() {
        currentPageSize = 0
        getFilmsFromApi()
    }

    private fun getFilmsFromApi() {
        repository.getFilmsFromApi(currentPageSize, object : Repository.GetFilmsFromApiCallback {
            override fun onSuccess(films: List<Film>) {
                repository.saveFilmsToDb(films)
                mutableFilmsLiveData.postValue(repository.getFilmsWithPagination(currentPageSize))
                mutableErrorLiveData.value = null

            }

            override fun onFailure(str: String) {
                mutableFilmsLiveData.postValue(repository.getFilmsWithPagination(currentPageSize))
                mutableErrorLiveData.postValue(str)

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