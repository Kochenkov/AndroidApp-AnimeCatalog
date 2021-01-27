package com.vkochenkov.filmscatalog.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.data.DataStorage
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.recycler.FilmsAdapter
import com.vkochenkov.filmscatalog.model.Film

class FilmsListActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val FILM = "FILM"
    }

    var filmsArr = DataStorage.filmsArr

    lateinit var filmsRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films_list)

        initFields()
        initRecycler()
        setOnClickListeners()
    }

    private fun initFields() {
        filmsRecycler = findViewById(R.id.films_list)
    }

    private fun initRecycler() {
        filmsRecycler.layoutManager = LinearLayoutManager(this)
        filmsRecycler.adapter = FilmsAdapter(filmsArr) { film ->
            openSelectedFilmActivity(film)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_share -> shareFriends()
        }
    }

    private fun setOnClickListeners() {
        findViewById<View>(R.id.btn_share).setOnClickListener(this)
    }

    private fun openSelectedFilmActivity(film: Film) {
        val intent = Intent(this, FilmInfoActivity::class.java).apply {
            putExtra(FILM, film)
        }
        startActivity(intent)
    }

    private fun shareFriends() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
        }
        startActivity(Intent.createChooser(intent, null))
    }
}