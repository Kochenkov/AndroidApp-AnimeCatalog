package com.vkochenkov.filmscatalog

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.vkochenkov.filmscatalog.model.Film

class FilmsListActivity : AppCompatActivity() {

    lateinit var imagesArr: Array<Int>
    lateinit var titlesArr: Array<Int>
    lateinit var descriptionsArr: Array<Int>

    lateinit var filmsArr: Array<Film>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films_list)

        createData()
    }

    private fun createData() {
        filmsArr = arrayOf(
            Film(R.string.film_title_1, R.string.film_description_1, R.drawable.im_film_1),
            Film(R.string.film_title_2, R.string.film_description_2, R.drawable.im_film_2),
            Film(R.string.film_title_3, R.string.film_description_3, R.drawable.im_film_3)
        )

        titlesArr = arrayOf(R.id.tv_title_film_1, R.id.tv_title_film_2, R.id.tv_title_film_3)
        imagesArr = arrayOf(R.id.imv_film_1, R.id.imv_film_2, R.id.imv_film_3)

        for (i in 0..2) {
            val textView = findViewById<TextView>(titlesArr[i])
            textView.text = getString(filmsArr[i].titleRes)

            val imageView = findViewById<ImageView>(imagesArr[i])
            imageView.setImageResource(filmsArr[i].imageRes)
        }
    }
}