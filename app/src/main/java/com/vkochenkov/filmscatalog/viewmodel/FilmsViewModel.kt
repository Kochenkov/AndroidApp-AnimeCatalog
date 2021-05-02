package com.vkochenkov.filmscatalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.filmscatalog.model.LocalDataStore.currentPageSize
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.model.Repository
import javax.inject.Inject

class FilmsViewModel @Inject constructor(val repository: Repository) : ViewModel() {
class FilmsViewModel : CommonViewModel() {

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
                repository.getFilmsWithPagination(currentPageSize, object : Repository.GetFilmsFromDatabaseCallback {
                    override fun onSuccess(films: List<Film>) {
                        mutableFilmsLiveData.postValue(films)
                    }
                })
                mutableErrorLiveData.value = null

            }

            override fun onFailure(str: String) {
                repository.getFilmsWithPagination(currentPageSize, object : Repository.GetFilmsFromDatabaseCallback {
                    override fun onSuccess(films: List<Film>) {
                        mutableFilmsLiveData.postValue(films)
                    }
                })
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