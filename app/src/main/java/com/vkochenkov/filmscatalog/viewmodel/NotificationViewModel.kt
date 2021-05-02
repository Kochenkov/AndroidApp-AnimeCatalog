package com.vkochenkov.filmscatalog.viewmodel

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.android.parcel.Parcelize

@Parcelize
class NotificationViewModel : CommonViewModel(), Parcelable {

    private var mutableNotifyFilmLiveData = MutableLiveData<Long>()

    val notifyFilmLiveData: LiveData<Long>
        get() = mutableNotifyFilmLiveData

    fun isNotifyFilm(name: String) {
        mutableNotifyFilmLiveData.postValue(repository.getFilm(name).notificationDate)
    }

    fun setNotificationFilm(name: String, date: Long) {
        repository.setNotificationFilm(name, date)
    }
}