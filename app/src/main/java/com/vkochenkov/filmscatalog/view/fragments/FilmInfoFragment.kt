package com.vkochenkov.filmscatalog.view.fragments

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
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
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.vkochenkov.filmscatalog.view.MainActivity.Companion.FILM
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
        descriptorView = view.findViewById(R.id.tv_description_film)
    }

    private fun getBundleWithFilmInfo() {
        val bundle = arguments
        film = bundle?.getParcelable(FILM)!!
    }

    private fun fillFieldsWithData() {
        toolbar.title = film.title
        descriptorView.text = film.description

        Glide.with(this)
            .asBitmap()
            .load(film.imageUrl)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    imageView.setImageBitmap(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                    //todo
                }
            })
    }
}