package com.vkochenkov.filmscatalog.view.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vkochenkov.filmscatalog.view.MainActivity
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.StoreSelectedFilm
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.view.recycler.favourites.FavouriteFilmItemClickListener
import com.vkochenkov.filmscatalog.view.recycler.favourites.FavouriteFilmsAdapter
import com.vkochenkov.filmscatalog.viewmodel.FavouriteFilmsViewModel

class FavouriteFilmsListFragment : Fragment() {

    private val favouritesFilmsViewModel by lazy {
        ViewModelProviders.of(this).get(FavouriteFilmsViewModel::class.java)
    }

    private lateinit var favouriteFilmsRecycler: RecyclerView
    private lateinit var emptyListTextView: TextView
    private lateinit var mainToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite_films_list, container, false)

        initFields(view)
        initRecycler(view)
        showStubIfListEmpty()

        return view
    }

    override fun onResume() {
        super.onResume()
        mainToolbar.setTitle(R.string.favourites_title_text)
    }

    private fun openSelectedFilmFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.FILM, film)

        val filmInfoFragment = FilmInfoFragment()
        filmInfoFragment.arguments = bundle

        (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragments_container, filmInfoFragment)
            .addToBackStack("FilmInfoFragment")
            .commit()
    }

    private fun initFields(view: View) {
        favouriteFilmsRecycler = view.findViewById(R.id.favourite_films_list)
        emptyListTextView = view.findViewById(R.id.empty_favourites_list_text)
        mainToolbar = (activity as AppCompatActivity).findViewById(R.id.main_toolbar)
    }

    private fun initRecycler(view: View) {
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            favouriteFilmsRecycler.layoutManager = LinearLayoutManager(view.context)
        } else {
            favouriteFilmsRecycler.layoutManager = GridLayoutManager(view.context, 2)
        }
        favouriteFilmsRecycler.adapter =
            FavouriteFilmsAdapter(
                object :
                    FavouriteFilmItemClickListener {
                    override fun detailsClickListener(film: Film) {

                        StoreSelectedFilm.currentSelectedFilm = film
                        favouriteFilmsRecycler.adapter?.notifyDataSetChanged()

                        openSelectedFilmFragment(film)
                    }

                    override fun deleteClickListener(film: Film, position: Int) {
                        deleteItemActions(film, position)
                        showSnackBar(film, position, view)
                    }
                })
        favouritesFilmsViewModel.favouriteFilmsLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                (favouriteFilmsRecycler.adapter as FavouriteFilmsAdapter).refreshDataList(it)
            }
        })
    }

    private fun showSnackBar(film: Film, position: Int, view: View) {
        val str = "${film.title} ${context?.getString(R.string.was_deleted_str)}"
        val snackbar = Snackbar.make(view, str, Snackbar.LENGTH_SHORT)
        snackbar.setAction(context?.getString(R.string.cancel_snackbar_str)) {
            restoreItemActions(film, position)
        }
        snackbar.show()
    }

    private fun deleteItemActions(film: Film, position: Int) {
        favouritesFilmsViewModel.unlikeFilm(film.serverName)
        favouriteFilmsRecycler.adapter?.notifyItemRemoved(position)
        favouriteFilmsRecycler.adapter?.notifyItemChanged(position)
    }

    private fun restoreItemActions(film: Film, position: Int) {
        favouritesFilmsViewModel.likeFilm(film.serverName)
        favouriteFilmsRecycler.adapter?.notifyItemRemoved(position)
        favouriteFilmsRecycler.adapter?.notifyItemChanged(position)
    }

    private fun showStubIfListEmpty() {
        favouritesFilmsViewModel.favouriteFilmsLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                emptyListTextView.visibility = View.VISIBLE
            }
            else {
                emptyListTextView.visibility = View.GONE
            }
        })
    }
}