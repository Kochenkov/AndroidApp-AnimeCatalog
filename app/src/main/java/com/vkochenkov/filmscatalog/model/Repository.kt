package com.vkochenkov.filmscatalog.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.model.api.ResponseFromApi
import com.vkochenkov.filmscatalog.model.entities.Film
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    fun getFilms() {
        if (App.instance?.database?.filmsDao()?.getAllFilms()==null) {
            getFilmsFromApi()
        }
        getFilmsFromDb()
    }

    fun getFavouritesFromRepository() {
        //todo get favourites from db
    }

    fun getFilmsFromDb(): LiveData<List<Film>> {
       return App.instance!!.database.filmsDao().getAllFilms()
    }

    fun getFilmsFromApi() {
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
                                imageUrl
                            )
                        )
                    }

                    //сохраняем список в базу
                    App.instance?.database?.filmsDao()?.insertAllFilms(filmsListFromApi)
                }
            }

            override fun onFailure(call: Call<ResponseFromApi>, t: Throwable) {
                Log.d("fromapi", t.message!!)
                //todo
            }
        })
    }
}