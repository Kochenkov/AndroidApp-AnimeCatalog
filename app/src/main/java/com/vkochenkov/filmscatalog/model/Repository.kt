package com.vkochenkov.filmscatalog.model

import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.api.ApiService.Companion.PAGES_SIZE
import com.vkochenkov.filmscatalog.model.api.ResponseFromApi
import com.vkochenkov.filmscatalog.model.db.Film
import io.reactivex.MaybeObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class Repository {

    val dao by lazy { App.instance!!.database.filmsDao() }
    val api by lazy { App.instance?.apiService!! }
    val appContext by lazy { App.instance?.applicationContext!! }

    fun getFilmsWithPagination(page: Int, callback: GetFilmsFromDatabaseCallback) {
        dao.getFilmsWithPagination(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(maybeObserver(callback))
    }

    fun getFavourites(callback: GetFilmsFromDatabaseCallback) {
        dao.getFavourites()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(maybeObserver(callback))
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
        api.getAnimeListWithPages(PAGES_SIZE, sincePage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ResponseFromApi> {
                override fun onSuccess(response: ResponseFromApi) {
                    val filmsListFromApi = ArrayList<Film>()
                    response.data.forEach {
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
                                false
                            )
                        )
                    }
                    callback.onSuccess(filmsListFromApi)
                }

                override fun onError(t: Throwable) {
                    callback.onFailure(appContext.getString(R.string.api_error_connection_str))
                }

                override fun onSubscribe(d: Disposable) {}

            })
    }

    interface GetFilmsFromApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure(str: String)
    }

    interface GetFilmsFromDatabaseCallback {
        fun onSuccess(films: List<Film>)
    }

    private fun maybeObserver(callback: GetFilmsFromDatabaseCallback) =
        object : MaybeObserver<List<Film>> {
            override fun onSuccess(data: List<Film>) {
                callback.onSuccess(data)
            }

            override fun onError(t: Throwable) {}
            override fun onSubscribe(d: Disposable) {}
            override fun onComplete() {}
        }
}