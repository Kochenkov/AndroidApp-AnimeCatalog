package com.vkochenkov.filmscatalog.model.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://kitsu.io/api/edge/"
        const val PAGES_SIZE = 10
    }

    @GET("anime")
    fun getAnimeListWithPages(@Query("page[limit]") items: Int,
                              @Query("page[offset]") sincePage: Int
    ): Call<ResponseFromApi>
}