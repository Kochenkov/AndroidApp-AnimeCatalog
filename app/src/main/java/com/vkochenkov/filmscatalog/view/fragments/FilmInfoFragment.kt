package com.vkochenkov.filmscatalog.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.view.MainActivity.Companion.FILM

class FilmInfoFragment : Fragment() {

    private lateinit var film: Film

    private lateinit var imageView: ImageView
    private lateinit var toolbar: Toolbar
    private lateinit var descriptorView: TextView
    private lateinit var startDateValueView: TextView
    private lateinit var averageRatingValueView: TextView
    private lateinit var ageRatingValueView: TextView
    private lateinit var episodeCountValueView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_film_info, container, false)

        hideMainBottomAndToolBars()
        initFields(view)
        setToolbar()
        getBundleWithFilmInfo()
        fillFieldsWithData()

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        showMainBottomAndToolBars()
    }

    private fun hideMainBottomAndToolBars() {
        (activity as AppCompatActivity).findViewById<View>(R.id.bottom_nav_view).visibility =
            View.GONE
        (activity as AppCompatActivity).findViewById<View>(R.id.main_toolbar).visibility =
            View.GONE
    }

    private fun showMainBottomAndToolBars() {
        (activity as AppCompatActivity).findViewById<View>(R.id.bottom_nav_view).visibility =
            View.VISIBLE
        (activity as AppCompatActivity).findViewById<View>(R.id.main_toolbar).visibility =
            View.VISIBLE
    }

    private fun setToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
    }

    private fun initFields(view: View) {
        toolbar = view.findViewById(R.id.toolbar_film_info)
        imageView = view.findViewById(R.id.image_view)
        descriptorView = view.findViewById(R.id.tv_description_value)
        startDateValueView = view.findViewById(R.id.tv_start_date_value)
        averageRatingValueView = view.findViewById(R.id.tv_average_rating_value)
        ageRatingValueView = view.findViewById(R.id.tv_age_rating_value)
        episodeCountValueView = view.findViewById(R.id.tv_episode_count_value)
    }

    private fun getBundleWithFilmInfo() {
        val bundle = arguments
        film = bundle?.getParcelable(FILM)!!
    }

    private fun fillFieldsWithData() {
        toolbar.title = film.title
        descriptorView.text = film.description

        startDateValueView.text = film.startDate
        averageRatingValueView.text = film.averageRating.toString()
        ageRatingValueView.text = film.ageRating
        episodeCountValueView.text = film.episodeCount.toString()

        Glide.with(App.instance!!.applicationContext)
            .load(film.imageUrl)
            .placeholder(R.drawable.im_default_film)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
}