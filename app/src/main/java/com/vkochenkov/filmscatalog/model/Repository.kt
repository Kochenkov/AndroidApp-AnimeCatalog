package com.vkochenkov.filmscatalog.model

import androidx.lifecycle.LiveData
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.model.api.ApiService.Companion.PAGES_SIZE
import com.vkochenkov.filmscatalog.model.api.ResponseFromApi
import com.vkochenkov.filmscatalog.model.db.Film
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    fun getFilmsWithPagination(page: Int): List<Film>? {
        return App.instance!!.database.filmsDao().getFilmsWithPagination(page)
    }

    fun getFavouritesWithPagination(page: Int): List<Film>? {
        return App.instance!!.database.filmsDao().getFavouritesWithPagination(page)
    }

    fun getFavourites(): LiveData<List<Film>> {
        return App.instance!!.database.filmsDao().getLikedFilms()
    }

    fun likeFilm(name: String) {
        App.instance?.database?.filmsDao()?.setLikedFilm(name)
    }

    fun unlikeFilm(name: String) {
        App.instance?.database?.filmsDao()?.setUnlikedFilm(name)
    }

    fun saveFilmsToDb(films: List<Film>) {
        App.instance?.database?.filmsDao()?.insertAllFilms(films)
    }

    fun getFilmsFromApi(sincePage: Int, callback: GetFilmsFromApiCallback) {
        App.instance?.apiService?.getAnimeListWithPages(PAGES_SIZE, sincePage)?.enqueue(object : Callback<ResponseFromApi> {
            override fun onResponse(
                call: Call<ResponseFromApi>,
                response: Response<ResponseFromApi>
            ) {
                if (response.isSuccessful) {

                    val filmsListFromApi = ArrayList<Film>()

                    for (i in response.body()!!.data.indices) {
                        val serverName = response.body()!!.data.get(i).attributes.serverName
                        val title = response.body()!!.data.get(i).attributes.title
                        val description = response.body()!!.data.get(i).attributes.description
                        val imageUrl = response.body()!!.data.get(i).attributes.posterImage.original

                        filmsListFromApi.add(
                            Film(
                                serverName,
                                title,
                                description,
                                imageUrl,
                                false
                            )
                        )
                    }

                    callback.onSuccess(filmsListFromApi)
                } else {
                    //todo вынести тексты в ресурсы
                    callback.onFailure("Не удалось загрузить данные. Код ошибки: " + response.code().toString())
                }
            }

            override fun onFailure(call: Call<ResponseFromApi>, t: Throwable) {
                callback.onFailure("Не удалось подключиться к серверу. Приложение работает в оффлайн-режиме.")
            }
        })
    }


    interface GetFilmsFromApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure(str: String)
    }
}