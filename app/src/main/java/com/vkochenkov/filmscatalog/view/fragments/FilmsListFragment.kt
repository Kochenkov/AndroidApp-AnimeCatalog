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
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.Repository
import com.vkochenkov.filmscatalog.model.StoreSelectedFilm
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

    var pages: Int = 0


    private lateinit var filmsRecycler: RecyclerView
    private lateinit var mainToolbar: Toolbar

    private lateinit var nestedScrollView: NestedScrollView
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

        return view
    }

    override fun onResume() {
        super.onResume()
        filmsRecycler.adapter?.notifyDataSetChanged()
        mainToolbar.setTitle(R.string.app_name)
    }

    private fun initSwipeToRefresh() {
        swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            App.instance?.repository?.getFilmsFromApi(pages, object : Repository.GetFilmsFromApiCallback {
                override fun onSuccess(films: List<Film>) {
                    App.instance?.repository?.saveFilmsToDb(films)
                    swipeRefresh.isRefreshing = false
                }

                override fun onFailure(str: String) {
                    Toast.makeText(App.instance?.applicationContext, str, Toast.LENGTH_SHORT).show()
                    swipeRefresh.isRefreshing = false
                }
            })
        })
    }


    private fun initFields(view: View) {
        filmsRecycler = view.findViewById(R.id.films_list)
        mainToolbar = (activity as AppCompatActivity).findViewById(R.id.main_toolbar)
        progressBar = view.findViewById(R.id.films_progress_bar)
        nestedScrollView = view.findViewById(R.id.films_nested_scroll)
        swipeRefresh = view.findViewById(R.id.films_swipe_refresh)
    }

    private fun initPagination() {
        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY==v.getChildAt(0).measuredHeight - v.measuredHeight) {
                Log.d("logg", "конец страницы!")

                //todo

                //показывать прогресс бар

                //инкрементить пейдж
                pages += 10

                //делать запрос к апихе с новыми пейджами
                App.instance?.repository?.getFilmsFromApi(pages, object : Repository.GetFilmsFromApiCallback {
                    override fun onSuccess(films: List<Film>) {
                        App.instance?.repository?.saveFilmsToDb(films)
                    }

                    override fun onFailure(str: String) {
                        Toast.makeText(App.instance?.applicationContext, str, Toast.LENGTH_SHORT).show()
                    }
                })
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
            FilmsAdapter(object :
                FilmItemClickListener {
                override fun detailsClickListener(film: Film) {

                    StoreSelectedFilm.currentSelectedFilm = film
                    filmsRecycler.adapter?.notifyDataSetChanged()

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