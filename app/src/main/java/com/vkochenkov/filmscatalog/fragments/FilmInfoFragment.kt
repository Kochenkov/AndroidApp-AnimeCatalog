package com.vkochenkov.filmscatalog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.vkochenkov.filmscatalog.MainActivity.Companion.FILM
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.Film

class FilmInfoFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var toolbar: Toolbar
    private lateinit var descriptorView: TextView
    private lateinit var film: Film

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).findViewById<View>(R.id.bottom_nav_view).visibility =
            View.GONE

        val view = inflater.inflate(R.layout.fragment_film_info, container, false)

        initFields(view)
        setToolbar()
        fillFieldsWithData()

        return view
    }

    private fun setToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
    }

    private fun initFields(view: View) {
        imageView = view.findViewById(R.id.image_view)
        toolbar = view.findViewById(R.id.toolbar_film_info)
        descriptorView = view.findViewById(R.id.tv_description_film)
        val bundle = arguments
        film = bundle?.getParcelable(FILM)!!
    }

    private fun fillFieldsWithData() {
        imageView.setImageResource(film.imageRes)
        toolbar.title = getText(film.titleRes)
        descriptorView.text = getText(film.descriptionRes)
    }
}