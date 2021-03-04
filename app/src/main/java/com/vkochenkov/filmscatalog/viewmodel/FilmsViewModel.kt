package com.vkochenkov.filmscatalog.viewmodel

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vkochenkov.filmscatalog.App
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

    fun getFilmsWithPagging(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
        repository.getFilmsFromApi(0, object : Repository.GetFilmsFromApiCallback {
            override fun onSuccess(films: List<Film>) {

                repository.saveFilmsToDb(films)

                mutableFilmsLiveData.postValue(repository.getFilmsWithPagination())

                progressBar.visibility = View.INVISIBLE

            }

            override fun onFailure(str: String) {

                mutableFilmsLiveData.postValue(repository.getFilmsWithPagination())
                mutableErrorLiveData.postValue(str)

                progressBar.visibility = View.INVISIBLE
            }
        })
    }

    fun getFilmsWithPagging(swipeRefresh: SwipeRefreshLayout) {
        swipeRefresh.isRefreshing = true
        repository.getFilmsFromApi(0, object : Repository.GetFilmsFromApiCallback {
            override fun onSuccess(films: List<Film>) {

                repository.saveFilmsToDb(films)

                mutableFilmsLiveData.postValue(repository.getFilmsWithPagination())

                swipeRefresh.isRefreshing = false


            }

            override fun onFailure(str: String) {

                mutableFilmsLiveData.postValue(repository.getFilmsWithPagination())
                mutableErrorLiveData.postValue(str)

                swipeRefresh.isRefreshing = false

            }
        })
    }


    fun likeFilm(name: String) {
        repository.likeFilm(name)
    }

    fun unlikeFilm(name: String) {
        repository.unlikeFilm(name)
    }

    fun getPaggingDataFromDb() {
        mutableFilmsLiveData.postValue(repository.getFilmsWithPagination())
    }

    fun getPaggingDataFromDb(page: Int) {
        mutableFilmsLiveData.postValue(repository.getFilmsWithPagination(page))
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