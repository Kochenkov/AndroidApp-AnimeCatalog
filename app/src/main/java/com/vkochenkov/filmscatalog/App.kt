package com.vkochenkov.filmscatalog

import android.app.Application
import com.google.gson.Gson
import com.vkochenkov.filmscatalog.data.api.ApiService
import com.vkochenkov.filmscatalog.data.api.ApiService.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class App: Application() {

    companion object {
        var instance: App? = null
    }

    lateinit var apiService: ApiService

    override fun onCreate() {
        super.onCreate()

        instance = this

        initRetrofit()
    }

    private fun initRetrofit() {

        apiService = Retrofit.Builder()
           // .client(OkHttpClient.Builder().build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    }

}