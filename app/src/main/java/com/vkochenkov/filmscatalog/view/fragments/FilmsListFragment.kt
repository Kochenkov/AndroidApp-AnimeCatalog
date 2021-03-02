package com.vkochenkov.filmscatalog.view.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.view.MainActivity.Companion.FILM
import com.vkochenkov.filmscatalog.view.recycler.main.FilmItemClickListener
import com.vkochenkov.filmscatalog.view.recycler.main.FilmsAdapter
import com.vkochenkov.filmscatalog.viewmodel.FilmsViewModel

class FilmsListFragment : Fragment() {

    //инициализируем вью-модель
    private val filmsViewModel by lazy {
        ViewModelProviders.of(this).get(FilmsViewModel::class.java)
    }

    private lateinit var filmsRecycler: RecyclerView
    private lateinit var mainToolbar: Toolbar

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
        mainToolbar.setTitle(R.string.app_name)
    }


    private fun initFields(view: View) {
        filmsRecycler = view.findViewById(R.id.films_list)
        mainToolbar = (activity as AppCompatActivity).findViewById(R.id.main_toolbar)
    }

    private fun initRecycler(view: View) {
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            filmsRecycler.layoutManager = LinearLayoutManager(view.context)
        } else {
            filmsRecycler.layoutManager = GridLayoutManager(view.context, 2)
        }

        filmsRecycler.adapter =
            FilmsAdapter(object :
                FilmItemClickListener {
                override fun detailsClickListener(film: Film) {


                    //   DataStorage.previousSelectedFilm = DataStorage.currentSelectedFilm
                    //   DataStorage.previousSelectedFilm?.selected = false
                    //  film.selected = true
                    //  DataStorage.currentSelectedFilm = film

                   // filmsRecycler.adapter?.notifyDataSetChanged()

                    openSelectedFilmFragment(film)
                }

                override fun likeClickListener(film: Film, position: Int) {
                    if (film.liked) {
                        filmsViewModel.unlikeFilm(film.serverName)
                    } else {
                        filmsViewModel.likeFilm(film.serverName)
                    }
                    filmsRecycler.adapter?.notifyItemChanged(position)
                }
            })

        //подписыаем адаптер на изменение списка
        filmsViewModel.getFilms().observe(viewLifecycleOwner, Observer {
            it?.let {
                (filmsRecycler.adapter as FilmsAdapter).refreshDataList(it)
            }
        })
    }

    private fun openSelectedFilmFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable(FILM, film)

        val filmInfoFragment = FilmInfoFragment()
        filmInfoFragment.arguments = bundle

        (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragments_container, filmInfoFragment)
            .addToBackStack("FilmInfoFragment")
            .commit()
    }
}