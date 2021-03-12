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
        dao.setLikedFilm(name)
    }

    fun unlikeFilm(name: String) {
        dao.setUnlikedFilm(name)
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

                    for (i in response.body()!!.data.indices) {
                        filmsListFromApi.add(
                            Film(
                                response.body()!!.data.get(i).attributes.serverName,
                                response.body()!!.data.get(i).attributes.title,
                                response.body()!!.data.get(i).attributes.description,
                                response.body()!!.data.get(i).attributes.posterImage.original,
                                response.body()!!.data.get(i).attributes.startDate,
                                response.body()!!.data.get(i).attributes.ageRating,
                                response.body()!!.data.get(i).attributes.episodeCount,
                                response.body()!!.data.get(i).attributes.averageRating,
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

    interface GetFilmsFromApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure(str: String)
    }
}