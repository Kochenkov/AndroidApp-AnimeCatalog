package com.vkochenkov.filmscatalog.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.data.DataStorage
import com.vkochenkov.filmscatalog.model.Film
import com.vkochenkov.filmscatalog.recycler.FavouriteFilmsAdapter

class FavouriteFilmsListActivity : AppCompatActivity() {

    lateinit var favouriteFilmsRecycler: RecyclerView
    lateinit var emptyListTextView: TextView
    lateinit var favouritesToolbar: Toolbar

    var favouriteFilmsList = DataStorage.favouriteFilmsList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_films_list)

        initFields()
        initRecycler()
        setToolbar()
    }

    private fun openSelectedFilmActivity(film: Film) {
        val intent = Intent(this, FilmInfoActivity::class.java).apply {
            putExtra(FilmsListActivity.FILM, film)
        }
        startActivity(intent)
    }

    private fun setToolbar() {
        setSupportActionBar(favouritesToolbar)
        favouritesToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initFields() {
        favouriteFilmsRecycler = findViewById(R.id.favourite_films_list)
        emptyListTextView = findViewById(R.id.empty_favourites_list_text)
        favouritesToolbar = findViewById(R.id.favourites_toolbar)
    }

    private fun initRecycler() {
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            favouriteFilmsRecycler.layoutManager = LinearLayoutManager(this)
        } else {
            favouriteFilmsRecycler.layoutManager = GridLayoutManager(this, 2)
        }
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