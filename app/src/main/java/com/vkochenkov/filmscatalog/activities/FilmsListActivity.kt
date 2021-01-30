package com.vkochenkov.filmscatalog.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.data.DataStorage
import com.vkochenkov.filmscatalog.dialogs.ExitDialog
import com.vkochenkov.filmscatalog.model.Film
import com.vkochenkov.filmscatalog.recycler.FilmsAdapter

class FilmsListActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val FILM = "FILM"
    }

    private var filmsArr = DataStorage.filmsArr

    private lateinit var filmsRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_films_list)

        initFields()
        initRecycler()
        setOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        filmsRecycler.adapter?.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_share -> shareFriends()
            R.id.btn_favourites -> openFavouritesFilmsListActivity()
        }
    }

    override fun onBackPressed() {
        showExitDialog()
    }

    private fun showExitDialog() {
        val exitDialog = ExitDialog(this, this)
        exitDialog.show()
    }

    private fun initFields() {
        filmsRecycler = findViewById(R.id.films_list)
    }

    private fun initRecycler() {
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            filmsRecycler.layoutManager = LinearLayoutManager(this)
        } else {
            filmsRecycler.layoutManager = GridLayoutManager(this, 2)
        }
        filmsRecycler.adapter = FilmsAdapter(filmsArr) { film ->
            openSelectedFilmActivity(film)
        }
    }

    private fun setOnClickListeners() {
        findViewById<View>(R.id.btn_share).setOnClickListener(this)
        findViewById<View>(R.id.btn_favourites).setOnClickListener(this)
    }

    private fun openSelectedFilmActivity(film: Film) {
        val intent = Intent(this, FilmInfoActivity::class.java).apply {
            putExtra(FILM, film)
        }
        startActivity(intent)
    }

    private fun openFavouritesFilmsListActivity() {
        val intent = Intent(this, FavouriteFilmsListActivity::class.java)
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