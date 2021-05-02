package com.vkochenkov.filmscatalog.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.di.ViewModelFactory
import com.vkochenkov.filmscatalog.model.Repository
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.view.MainActivity.Companion.FILM
import com.vkochenkov.filmscatalog.view.dialogs.WatchLaterDialogFragment
import com.vkochenkov.filmscatalog.viewmodel.NotificationViewModel
import java.text.SimpleDateFormat
import javax.inject.Inject

class FilmInfoFragment : Fragment() {

    @Inject
    lateinit var repository: Repository

    private val notificationInfoViewModel: NotificationViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(repository)).get(NotificationViewModel::class.java)
    }

    private lateinit var film: Film

    private lateinit var imageView: ImageView
    private lateinit var toolbar: Toolbar
    private lateinit var descriptorView: TextView
    private lateinit var startDateValueView: TextView
    private lateinit var averageRatingValueView: TextView
    private lateinit var ageRatingValueView: TextView
    private lateinit var episodeCountValueView: TextView
    private lateinit var btnWatchLater: Button
    private lateinit var tvNotificationDate: TextView
    private lateinit var tvNotificationDateText: TextView

    init {
        App.appComponent.inject(this)
    }

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
        btnWatchLater.setOnClickListener(watchLaterBtnClickListener())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnNotifyChangeObserver()
        notificationInfoViewModel.isNotifyFilm(film.serverName)
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
        btnWatchLater = view.findViewById(R.id.btn_watch_later)
        tvNotificationDate = view.findViewById(R.id.tv_notification_date)
        tvNotificationDateText = view.findViewById(R.id.tv_notification_date_str)
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

        Glide.with(context!!.applicationContext)
            .load(film.imageUrl)
            .placeholder(R.drawable.im_default_film)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    private fun watchLaterBtnClickListener() = View.OnClickListener {
        val dialogFragment = WatchLaterDialogFragment()
        dialogFragment.film = film
        dialogFragment.viewModel = notificationInfoViewModel
        dialogFragment.show(fragmentManager!!, null)
    }

    private fun initOnNotifyChangeObserver() {
        notificationInfoViewModel.notifyFilmLiveData.observe(viewLifecycleOwner, Observer {
            if (it != 0L) {
                val dataFormat = SimpleDateFormat("dd MMM yyyy hh.mm a")
                btnWatchLater.visibility = View.INVISIBLE
                tvNotificationDate.text = dataFormat.format(it)
                tvNotificationDate.visibility = View.VISIBLE
                tvNotificationDateText.visibility = View.VISIBLE
            } else {
                btnWatchLater.visibility = View.VISIBLE
                tvNotificationDate.visibility = View.INVISIBLE
                tvNotificationDateText.visibility = View.INVISIBLE
            }
        })
    }
}