package com.vkochenkov.filmscatalog

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.model.db.FilmsDao
import com.vkochenkov.filmscatalog.model.db.FilmsDatabase
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class DbTests {

    lateinit var database: FilmsDatabase
    lateinit var dao: FilmsDao

    val filmName = "testName"
    val film = Film(
        filmName,
        "test",
        "test",
        "test",
        "test",
        "test",
        10,
        99.0,
        false,
        0L
    )
    val filmsList: MutableList<Film> = ArrayList()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FilmsDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.filmsDao()

        filmsList.add(film)
        dao.insertAllFilms(filmsList)
    }

    @Test
    fun `проверка получения фильма по имени из базы`() {
        dao.getFilm(filmName).subscribe(object : MaybeObserver<Film> {
            override fun onSuccess(f: Film) {
                assertEquals(filmName, f.serverName)
            }
            override fun onComplete() {}
            override fun onSubscribe(d: Disposable) {}
            override fun onError(t: Throwable) {}
        })
    }

    @Test
    fun `проверка избранного - добавление и удаление фильма`() {
        dao.setLikeFilm(filmName)
        dao.getFilm(filmName).subscribe(object : MaybeObserver<Film> {
            override fun onSuccess(f: Film) {
                assertEquals(true, f.liked)
            }
            override fun onComplete() {}
            override fun onSubscribe(d: Disposable) {}
            override fun onError(t: Throwable) {}
        })

        dao.setUnlikeFilm(filmName)
        dao.getFilm(filmName).subscribe(object : MaybeObserver<Film> {
            override fun onSuccess(f: Film) {
                assertEquals(false, f.liked)
            }
            override fun onComplete() {}
            override fun onSubscribe(d: Disposable) {}
            override fun onError(t: Throwable) {}
        })
    }

    @Test
    fun `проверка нотификации - установка и очистка`() {
        val date = System.currentTimeMillis()

        dao.setNotificationFilm(filmName, date)
        dao.getFilm(filmName).subscribe(object : MaybeObserver<Film> {
            override fun onSuccess(f: Film) {
                assertEquals(date, f.notificationDate)
            }
            override fun onComplete() {}
            override fun onSubscribe(d: Disposable) {}
            override fun onError(t: Throwable) {}
        })

        dao.clearNotificationFilm(filmName)
        dao.getFilm(filmName).subscribe(object : MaybeObserver<Film> {
            override fun onSuccess(f: Film) {
                assertEquals(0L, f.notificationDate)
            }
            override fun onComplete() {}
            override fun onSubscribe(d: Disposable) {}
            override fun onError(t: Throwable) {}
        })
    }
}