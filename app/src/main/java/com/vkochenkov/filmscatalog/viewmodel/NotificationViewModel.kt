package com.vkochenkov.filmscatalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vkochenkov.filmscatalog.model.Repository
import com.vkochenkov.filmscatalog.model.db.Film
import javax.inject.Inject

class NotificationViewModel @Inject constructor(override val repository: Repository) : CommonViewModel(repository) {

    private var mutableNotifyFilmLiveData = MutableLiveData<Long>()

    val notifyFilmLiveData: LiveData<Long>
        get() = mutableNotifyFilmLiveData

    fun isNotifyFilm(name: String) {
        repository.getFilm(name, object : Repository.GetFilmFromDatabaseCallback{
            override fun onSuccess(film: Film) {
                mutableNotifyFilmLiveData.postValue(film.notificationDate)
            }
        })
    }

    fun setNotificationFilm(name: String, date: Long) {
        repository.setNotificationFilm(name, date)
    }
}