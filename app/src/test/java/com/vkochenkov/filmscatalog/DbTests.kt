package com.vkochenkov.filmscatalog

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.model.db.FilmsDao
import com.vkochenkov.filmscatalog.model.db.FilmsDatabase
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
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

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FilmsDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.filmsDao()
    }

    @Test
    fun dbShouldInsertAndGetFilm() {

        val filmsList: MutableList<Film> = ArrayList()
        filmsList.add(film)

        dao.insertAllFilms(filmsList)
        dao.getFilm(filmName).subscribe(object : MaybeObserver<Film> {
            override fun onSuccess(f: Film) {
                assertEquals(filmName, f.serverName)
            }
            override fun onComplete() {}
            override fun onSubscribe(d: Disposable) {}
            override fun onError(t: Throwable) {}
        })
    }

    //todo добавить еще тестов
}