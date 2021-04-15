package com.vkochenkov.filmscatalog

import android.app.Application
import androidx.room.Room
import com.vkochenkov.filmscatalog.model.Repository
import com.vkochenkov.filmscatalog.model.api.ApiService
import com.vkochenkov.filmscatalog.model.api.ApiService.Companion.BASE_URL
import com.vkochenkov.filmscatalog.model.db.FilmsDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object {
        var instance: App? = null
    }

    lateinit var apiService: ApiService
    lateinit var database: FilmsDatabase
    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()

        instance = this

        repository = Repository()
        initRetrofit()
        initDatabase()
    }

    private fun initRetrofit() {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.HEADERS

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

    private fun initDatabase() {
        database = Room.databaseBuilder(
            applicationContext,
            FilmsDatabase::class.java,
            "films_db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}