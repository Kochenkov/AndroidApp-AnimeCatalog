package com.vkochenkov.filmscatalog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.vkochenkov.filmscatalog.model.Film

class FilmsListActivity : AppCompatActivity(), View.OnClickListener{

    companion object {
        const val FILM = "FILM"
    }

    lateinit var imagesArr: Array<Int>
    lateinit var titlesArr: Array<Int>
    lateinit var buttonsArr: Array<Int>
    lateinit var filmsArr: Array<Film>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films_list)

        createAndFillData()
        setOnClickListeners()
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btn_details_1 -> openFilmActivity(filmsArr[0])
            R.id.btn_details_2 -> openFilmActivity(filmsArr[1])
            R.id.btn_details_3 -> openFilmActivity(filmsArr[2])
            R.id.btn_share -> shareFriends()
        }
    }

    private fun shareFriends() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
        }
        startActivity(Intent.createChooser(intent, null))
    }

    private fun setOnClickListeners() {
        for (element in buttonsArr) {
            findViewById<View>(element).setOnClickListener(this)
        }
    }

    private fun createAndFillData() {
        filmsArr = arrayOf(
            Film(R.string.film_title_1, R.string.film_description_1, R.drawable.im_film_1),
            Film(R.string.film_title_2, R.string.film_description_2, R.drawable.im_film_2),
            Film(R.string.film_title_3, R.string.film_description_3, R.drawable.im_film_3)
        )

        titlesArr = arrayOf(R.id.tv_title_film_1, R.id.tv_title_film_2, R.id.tv_title_film_3)
        imagesArr = arrayOf(R.id.imv_film_1, R.id.imv_film_2, R.id.imv_film_3)
        buttonsArr = arrayOf(R.id.btn_details_1, R.id.btn_details_2, R.id.btn_details_3, R.id.btn_share)

        for (i in 0..2) {
            val textView = findViewById<TextView>(titlesArr[i])
            textView.text = getString(filmsArr[i].titleRes)

            val imageView = findViewById<ImageView>(imagesArr[i])
            imageView.setImageResource(filmsArr[i].imageRes)
        }
    }

    private fun openFilmActivity(film: Film) {
        val intent = Intent(this, FilmInfoActivity::class.java).apply {
            putExtra(FILM, film)
        }
        startActivity(intent)
    }
}