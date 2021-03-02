package com.vkochenkov.filmscatalog.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.model.api.ResponseFromApi
import com.vkochenkov.filmscatalog.model.db.Film
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    fun getFavourites(): LiveData<List<Film>> {
        return App.instance!!.database.filmsDao().getLikedFilms()
    }

    fun likeFilm(name: String) {
        App.instance?.database?.filmsDao()?.setLikedFilm(name)
    }

    fun unlikeFilm(name: String) {
        App.instance?.database?.filmsDao()?.setUnlikedFilm(name)
    }

    fun getFilmsFromDb(): LiveData<List<Film>> {
        return App.instance!!.database.filmsDao().getAllFilms()
    }

    fun saveFilmsToDb(films: List<Film>) {
        App.instance?.database?.filmsDao()?.insertAllFilms(films)
    }

    fun getFilmsFromApi(callback: GetFilmsCallback) {
        App.instance?.apiService?.getAnimeList()?.enqueue(object : Callback<ResponseFromApi> {
            override fun onResponse(
                call: Call<ResponseFromApi>,
                response: Response<ResponseFromApi>
            ) {
                if (response.isSuccessful) {
                    Log.d("fromapi", "success")

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
                    callback.onFailure("Api response code is: " + response.code().toString())
                }
            }

            override fun onFailure(call: Call<ResponseFromApi>, t: Throwable) {
                callback.onFailure("Something went wrong")
            }
        })
    }

    interface GetFilmsCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure(str: String)
    }
}