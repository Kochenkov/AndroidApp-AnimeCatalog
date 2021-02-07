package com.vkochenkov.filmscatalog.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.activities.FilmsListActivity.Companion.FILM
import com.vkochenkov.filmscatalog.model.Film

class FilmInfoActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var toolbar: Toolbar
    private lateinit var descriptorView: TextView
    private lateinit var film: Film

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_info)

        initFields()
        setToolbar()
        fillFieldsWithData()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initFields() {
        imageView = findViewById(R.id.image_view)
        toolbar = findViewById(R.id.toolbar_film_info)
        descriptorView = findViewById(R.id.tv_description_film)
        film = intent.extras?.get(FILM) as Film
    }

    private fun fillFieldsWithData() {
        imageView.setImageResource(film.imageRes)
        toolbar.title = getText(film.titleRes)
        descriptorView.text = getText(film.descriptionRes)
    }
}