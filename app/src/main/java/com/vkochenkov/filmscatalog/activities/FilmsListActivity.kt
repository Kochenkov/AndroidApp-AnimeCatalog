package com.vkochenkov.filmscatalog.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.for_recycler.FilmsAdapter
import com.vkochenkov.filmscatalog.model.Film

class FilmsListActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val FILM = "FILM"
        const val CHOSEN_FILM_TITLE = "CHOSEN_FILM_TITLE"
    }

    private lateinit var filmsArr: Array<Film>
    lateinit var filmsRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films_list)

        initFields()
        initFilmsArray()
        initRecycler()
        setOnClickListeners()
    }

    private fun initFields() {
        filmsRecycler = findViewById(R.id.films_list)
    }

    private fun initRecycler() {
        filmsRecycler.layoutManager = LinearLayoutManager(this)
        filmsRecycler.adapter = FilmsAdapter(filmsArr) { film ->
            openFilmActivity(film)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_share -> shareFriends()
        }
    }

    //    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        chosenFilmTitle?.let { outState.putInt(CHOSEN_FILM_TITLE, it) }
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        chosenFilmTitle = savedInstanceState.getInt(CHOSEN_FILM_TITLE)
//        changeTitleColorForSelectedFilm()
//    }
//
    private fun setOnClickListeners() {
        findViewById<View>(R.id.btn_share).setOnClickListener(this)
    }

    private fun initFilmsArray() {
        filmsArr = arrayOf(
            Film(R.string.film_title_1, R.string.film_description_1, R.drawable.im_film_1),
            Film(R.string.film_title_2, R.string.film_description_2, R.drawable.im_film_2),
            Film(R.string.film_title_3, R.string.film_description_3, R.drawable.im_film_3),
            Film(R.string.film_title_4, R.string.film_description_4, R.drawable.im_film_4),
            Film(R.string.film_title_5, R.string.film_description_5, R.drawable.im_film_5),
            Film(R.string.film_title_6, R.string.film_description_6, R.drawable.im_film_6)
        )
    }

//    private fun clickOnFilmDetails(film: Film, titleId: Int) {
//        chosenFilmTitle = titleId
//        changeTitleColorForSelectedFilm()
//        openFilmActivity(film)
//    }

    private fun openFilmActivity(film: Film) {
        val intent = Intent(this, FilmInfoActivity::class.java).apply {
            putExtra(FILM, film)
        }
        startActivity(intent)
    }

//    private fun changeTitleColorForSelectedFilm() {
//        val accentColor = ContextCompat.getColor(this, R.color.colorAccent)
//        val defaultColor = ContextCompat.getColor(this, R.color.colorBlack)
//        for (element in titlesArr) {
//            if (element == chosenFilmTitle) {
//                findViewById<TextView>(element).setTextColor(accentColor)
//            } else {
//                findViewById<TextView>(element).setTextColor(defaultColor)
//            }
//        }
//    }

    private fun shareFriends() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
        }
        startActivity(Intent.createChooser(intent, null))
    }
}