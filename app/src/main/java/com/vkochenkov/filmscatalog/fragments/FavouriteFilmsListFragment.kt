package com.vkochenkov.filmscatalog.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.MainActivity
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.data.DataStorage
import com.vkochenkov.filmscatalog.model.Film
import com.vkochenkov.filmscatalog.recycler.FavouriteFilmsAdapter

class FavouriteFilmsListFragment : Fragment() {

    private lateinit var favouriteFilmsRecycler: RecyclerView
    private lateinit var emptyListTextView: TextView
    private lateinit var favouritesToolbar: Toolbar

    private var favouriteFilmsList = DataStorage.favouriteFilmsList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite_films_list, container, false)

        initFields(view)
        initRecycler(view)

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<View>(R.id.bottom_nav_view).visibility = View.VISIBLE
    }

    private fun openSelectedFilmFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.FILM, film)

        val filmInfoFragment = FilmInfoFragment()
        filmInfoFragment.arguments = bundle

        (activity as AppCompatActivity).findViewById<View>(R.id.bottom_nav_view).visibility = View.GONE

        (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragments_container, filmInfoFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initFields(view: View) {
        favouriteFilmsRecycler = view.findViewById(R.id.favourite_films_list)
        emptyListTextView = view.findViewById(R.id.empty_favourites_list_text)
        favouritesToolbar = view.findViewById(R.id.favourites_toolbar)
    }

    private fun initRecycler(view: View) {
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            favouriteFilmsRecycler.layoutManager = LinearLayoutManager(view.context)
        } else {
            favouriteFilmsRecycler.layoutManager = GridLayoutManager(view.context, 2)
        }
        favouriteFilmsRecycler.adapter =
            FavouriteFilmsAdapter(favouriteFilmsList, emptyListTextView) { film ->
                openSelectedFilmFragment(film)
            }
        if (favouriteFilmsList.isEmpty()) {
            emptyListTextView.visibility = View.VISIBLE
        } else {
            emptyListTextView.visibility = View.GONE
        }
    }
}