package com.vkochenkov.filmscatalog.model

import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.api.ApiService.Companion.PAGES_SIZE
import com.vkochenkov.filmscatalog.model.api.ResponseFromApi
import com.vkochenkov.filmscatalog.model.db.Film
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    val dao by lazy { App.instance!!.database.filmsDao() }
    val api by lazy { App.instance?.apiService!! }
    val appContext by lazy {App.instance?.applicationContext!!}

    fun getFilmsWithPagination(page: Int): List<Film>? {
        return dao.getFilmsWithPagination(page)
    }

    fun getFavourites(): List<Film> {
        return dao.getFavourites()
    }

    fun likeFilm(name: String) {
        dao.setLikeFilm(name)
    }

    fun unlikeFilm(name: String) {
        dao.setUnlikeFilm(name)
    }

    fun saveFilmsToDb(films: List<Film>) {
        dao.insertAllFilms(films)
    }

    fun getFilmsFromApi(sincePage: Int, callback: GetFilmsFromApiCallback) {
        api.getAnimeListWithPages(PAGES_SIZE, sincePage).enqueue(object : Callback<ResponseFromApi> {
            override fun onResponse(
                call: Call<ResponseFromApi>,
                response: Response<ResponseFromApi>
            ) {
                if (response.isSuccessful) {

                    val filmsListFromApi = ArrayList<Film>()

                        response.body()!!.data.forEach {
                            filmsListFromApi.add(
                                Film(
                                    it.attributes.serverName,
                                    it.attributes.title,
                                    it.attributes.description,
                                    it.attributes.posterImage.original,
                                    it.attributes.startDate,
                                    it.attributes.ageRating,
                                    it.attributes.episodeCount,
                                    it.attributes.averageRating,
                                    false,
                                    false
                                )
                            )
                        }

                    callback.onSuccess(filmsListFromApi)
                } else {
                    callback.onFailure(appContext.getString(R.string.api_error_server_str) + " " + response.code())
                }
            }

            override fun onFailure(call: Call<ResponseFromApi>, t: Throwable) {
                callback.onFailure(appContext.getString(R.string.api_error_connection_str))
            }
        })
    }

    fun isNotifyFilm(name: String): Boolean {
        val film = dao.getFilm(name)
        return film.notify
    }

    fun notifyFilm(name: String) {
        dao.setNotifyFilm(name)
    }

    fun unnotifyFilm(name: String) {
        dao.setUnnotifyFilm(name)
    }

    interface GetFilmsFromApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure(str: String)
    }
}