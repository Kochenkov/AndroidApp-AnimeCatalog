package com.vkochenkov.filmscatalog.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.data.Film
import com.vkochenkov.filmscatalog.data.api.pojo.ResponseFromApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmsViewModel: ViewModel() {

    val filmsList: MutableLiveData<List<Film>> = MutableLiveData()

    //получение данных
    fun updateFilmsList() {

        //не работает у меня при переключении между фрагментами (чищу бекстек?)
        if (filmsList.value==null) {
            getDataFromApi()
        }
    }

    //todo вынести в интерактор?
    fun getDataFromApi() {
        App.instance?.apiService?.getAnimeList()?.enqueue(object : Callback<ResponseFromApi> {
            override fun onResponse(
                call: Call<ResponseFromApi>,
                response: Response<ResponseFromApi>
            ) {
                Log.d("fromapi", "success")

                val filmsListFromApi = ArrayList<Film>()

                for (i in response.body()!!.data.indices) {
                    val title = response.body()!!.data.get(i).attributes.title
                    val description = response.body()!!.data.get(i).attributes.description
                    val imageUrl = response.body()!!.data.get(i).attributes.posterImage.original

                    filmsListFromApi.add(Film(title, description, imageUrl))
                }

                filmsList.value = filmsListFromApi

            }

            override fun onFailure(call: Call<ResponseFromApi>, t: Throwable) {
                Log.d("fromapi", t.message!!)
                //todo
            }
        })
    }
}