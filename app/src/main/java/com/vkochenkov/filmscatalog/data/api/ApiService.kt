package com.vkochenkov.filmscatalog.data.api

import com.vkochenkov.filmscatalog.data.api.pojo.ResponseFromApi
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    companion object {
        const val BASE_URL = "https://kitsu.io/api/edge/"
    }

    @GET("anime")
    fun getAnimeList(): Call<ResponseFromApi>
}