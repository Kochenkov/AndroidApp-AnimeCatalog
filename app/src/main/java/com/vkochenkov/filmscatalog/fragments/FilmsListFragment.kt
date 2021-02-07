package com.vkochenkov.filmscatalog.fragments

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.MainActivity.Companion.FILM
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.data.DataStorage
import com.vkochenkov.filmscatalog.model.Film
import com.vkochenkov.filmscatalog.recycler.FilmsAdapter

class FilmsListFragment : Fragment() {

    private var filmsArr = DataStorage.filmsArr

    private lateinit var filmsRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_films_list, container, false)

        initFields(view)
        initRecycler(view)

        return view
    }

    override fun onResume() {
        super.onResume()
        filmsRecycler.adapter?.notifyDataSetChanged()
        (activity as AppCompatActivity).findViewById<View>(R.id.bottom_nav_view).visibility = View.VISIBLE
    }

    private fun initFields(view: View) {
        filmsRecycler = view.findViewById(R.id.films_list)
    }

    private fun initRecycler(view: View) {
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            filmsRecycler.layoutManager = LinearLayoutManager(view.context)
        } else {
            filmsRecycler.layoutManager = GridLayoutManager(view.context, 2)
        }
        filmsRecycler.adapter = FilmsAdapter(filmsArr) { film ->
            openSelectedFilmFragment(film)
        }
    }

    private fun openSelectedFilmFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable(FILM, film)

        val filmInfoFragment = FilmInfoFragment()
        filmInfoFragment.arguments = bundle

        (activity as AppCompatActivity).findViewById<View>(R.id.bottom_nav_view).visibility =
            View.GONE

        (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragments_container, filmInfoFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun shareFriends() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
        }
        startActivity(Intent.createChooser(intent, null))
    }
}