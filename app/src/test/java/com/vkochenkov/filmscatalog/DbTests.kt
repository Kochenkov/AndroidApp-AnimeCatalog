package com.vkochenkov.filmscatalog

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.model.db.FilmsDao
import com.vkochenkov.filmscatalog.model.db.FilmsDatabase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

// todo долго пытался решить проблему, но подобный тест не работает
// если запускать через Junit, то не может найти AppComponent
// если запускать через Android Instrumented Tests, то пишет "no tests were fount"
@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class DbTests {

    lateinit var database: FilmsDatabase
    lateinit var dao: FilmsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
            FilmsDatabase::class.java).allowMainThreadQueries().build()
        dao = database.filmsDao()
    }

    @Test
    @Config(sdk = [28])
    fun test() {
        val film: Film = Film(
            "test",
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
        filmsList.add(film)
        dao.insertAllFilms(filmsList)

        val test = dao.getFilm("test")

        System.out.println(test.toString())

        //добавить assert, если получится запустить тест
    }
}