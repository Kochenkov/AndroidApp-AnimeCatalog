package com.vkochenkov.filmscatalog.view.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.LocalDataStore
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

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_films_list, container, false)

        initFields(view)
        initRecycler(view)
        initPagination()
        initSwipeToRefresh()
        initOnErrorObserver()

        filmsViewModel.getFilmsWithPagging(progressBar)

        return view
    }

    override fun onResume() {
        super.onResume()
        filmsRecycler.adapter?.notifyDataSetChanged()
        mainToolbar.setTitle(R.string.app_name)
    }

    private fun initOnErrorObserver() {
        filmsViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initFields(view: View) {
        filmsRecycler = view.findViewById(R.id.films_list)
        mainToolbar = (activity as AppCompatActivity).findViewById(R.id.main_toolbar)
        progressBar = view.findViewById(R.id.films_progress_bar)
        swipeRefresh = view.findViewById(R.id.films_swipe_refresh)
    }

    private fun initSwipeToRefresh() {
        swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            filmsViewModel.getFilmsWithPagging(swipeRefresh)
        })
    }

    private fun initPagination() {
        filmsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    val visibleItemCount = filmsRecycler.layoutManager!!.getChildCount()
                    val totalItemCount = filmsRecycler.layoutManager!!.getItemCount()
                    val pastVisiblesItems = (filmsRecycler.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                        Log.d("loggg", "Last Item Wow !")
                        filmsViewModel.getFilmsWithPagging(progressBar)

                    }
                }
            }
        })
    }

    private fun initRecycler(view: View) {
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            filmsRecycler.layoutManager = LinearLayoutManager(view.context)
        } else {
            filmsRecycler.layoutManager = GridLayoutManager(view.context, 2)
        }

        filmsRecycler.adapter =
            FilmsAdapter(object : FilmItemClickListener {
                override fun detailsClickListener(film: Film) {

                    LocalDataStore.currentSelectedFilm = film
                    filmsRecycler.adapter?.notifyDataSetChanged()

                    openSelectedFilmFragment(film)
                }

                override fun likeClickListener(film: Film, position: Int) {
                    if (film.liked) {
                        film.liked = false
                        filmsViewModel.unlikeFilm(film.serverName)
                    } else {
                        film.liked = true
                        filmsViewModel.likeFilm(film.serverName)
                    }
                    filmsRecycler.adapter?.notifyItemChanged(position)
                }
            })

        //подписыаем адаптер на изменение списка
        filmsViewModel.filmsLiveData.observe(viewLifecycleOwner, Observer {
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