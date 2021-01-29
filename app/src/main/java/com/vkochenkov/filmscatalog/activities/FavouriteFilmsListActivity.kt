package com.vkochenkov.filmscatalog.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.data.DataStorage
import com.vkochenkov.filmscatalog.model.Film
import com.vkochenkov.filmscatalog.recycler.FavouriteFilmsAdapter

class FavouriteFilmsListActivity : AppCompatActivity() {

    lateinit var favouriteFilmsRecycler: RecyclerView
    lateinit var emptyListTextView: TextView

    var favouriteFilmsList = DataStorage.favouriteFilmsList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_films_list)

        initFields()
        initRecycler()
    }

    private fun openSelectedFilmActivity(film: Film) {
        val intent = Intent(this, FilmInfoActivity::class.java).apply {
            putExtra(FilmsListActivity.FILM, film)
        }
        startActivity(intent)
    }

    private fun initFields() {
        favouriteFilmsRecycler = findViewById(R.id.favourite_films_list)
        emptyListTextView = findViewById(R.id.empty_favourites_list_text)
    }

    private fun initRecycler() {
        favouriteFilmsRecycler.layoutManager = LinearLayoutManager(this)
        favouriteFilmsRecycler.adapter =
            FavouriteFilmsAdapter(favouriteFilmsList, emptyListTextView) { film ->
                openSelectedFilmActivity(film)
            }
        if (favouriteFilmsList.isEmpty()) {
            emptyListTextView.visibility = View.VISIBLE
        } else {
            emptyListTextView.visibility = View.GONE
        }
    }
}