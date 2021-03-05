package com.vkochenkov.filmscatalog.viewmodel

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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

    fun getFilmsWithPaging(progressBar: ProgressBar, shouldIncrementPageCount: Boolean = true) {
        if (shouldIncrementPageCount) {
            currentPageSize += 10
        }
        progressBar.visibility = View.VISIBLE
        repository.getFilmsFromApi(currentPageSize, object : Repository.GetFilmsFromApiCallback {
            override fun onSuccess(films: List<Film>) {

                repository.saveFilmsToDb(films)
                mutableFilmsLiveData.postValue(repository.getFilmsWithPagination(currentPageSize))
                mutableErrorLiveData.value = null

                progressBar.visibility = View.INVISIBLE
            }

            override fun onFailure(str: String) {

                mutableFilmsLiveData.postValue(repository.getFilmsWithPagination(currentPageSize))
                mutableErrorLiveData.postValue(str)

                progressBar.visibility = View.INVISIBLE
            }
        })
    }

    fun getFilmsWithPaging(swipeRefresh: SwipeRefreshLayout) {
        swipeRefresh.isRefreshing = true
        currentPageSize = 0
        repository.getFilmsFromApi(currentPageSize, object : Repository.GetFilmsFromApiCallback {
            override fun onSuccess(films: List<Film>) {

                repository.saveFilmsToDb(films)
                mutableFilmsLiveData.postValue(repository.getFilmsWithPagination(currentPageSize))
                mutableErrorLiveData.value = null

                swipeRefresh.isRefreshing = false
            }

            override fun onFailure(str: String) {

                mutableFilmsLiveData.postValue(repository.getFilmsWithPagination(currentPageSize))
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
}