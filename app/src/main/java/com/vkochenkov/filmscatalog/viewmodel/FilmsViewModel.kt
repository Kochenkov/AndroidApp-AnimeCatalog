package com.vkochenkov.filmscatalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.model.Repository

class FilmsViewModel : ViewModel() {

    private var repository: Repository = App.instance!!.repository

    fun getFilms(): LiveData<List<Film>>
    {
        return repository.getFilmsFromDb()
    }

    fun getFilmsFromApi(callback: Repository.GetFilmsCallback)
    {
        repository.getFilmsFromApi(callback)
    }

    fun likeFilm(name: String) {
        repository.likeFilm(name)
    }

    fun unlikeFilm(name: String) {
        repository.unlikeFilm(name)
    }

    fun saveFilmsToDb(films: List<Film>) {
        repository.saveFilmsToDb(films)
    }
}