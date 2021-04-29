package com.vkochenkov.filmscatalog.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vkochenkov.filmscatalog.model.Repository
import com.vkochenkov.filmscatalog.viewmodel.FavouriteFilmsViewModel
import com.vkochenkov.filmscatalog.viewmodel.FilmsViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FilmsViewModel::class.java) -> {
                FilmsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FavouriteFilmsViewModel::class.java) -> {
                FavouriteFilmsViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}