package com.vkochenkov.filmscatalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NotificationViewModel : CommonViewModel() {

    private var mutableNotifyFilmLiveData = MutableLiveData<Boolean>()

    val notifyFilmLiveData: LiveData<Boolean>
        get() = mutableNotifyFilmLiveData

    fun isNotifyFilm(name: String) {
        mutableNotifyFilmLiveData.postValue(repository.isNotifyFilm(name))
    }

    fun notifyFilm(name: String) {
        repository.notifyFilm(name)
    }

    fun unnotifyFilm(name: String) {
        repository.unnotifyFilm(name)
    }
}