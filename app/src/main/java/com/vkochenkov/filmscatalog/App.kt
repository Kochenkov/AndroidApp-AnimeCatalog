package com.vkochenkov.filmscatalog

import android.app.Application
import com.vkochenkov.filmscatalog.data.api.ApiService
import com.vkochenkov.filmscatalog.data.api.ApiService.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {

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

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val gsonConverterFactory = GsonConverterFactory.create()

        apiService = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(ApiService::class.java)
    }
}