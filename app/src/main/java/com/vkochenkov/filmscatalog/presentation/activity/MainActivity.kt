package com.vkochenkov.filmscatalog.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.Repository
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.presentation.dialogs.ExitDialog
import com.vkochenkov.filmscatalog.presentation.fragments.AppInfoFragment
import com.vkochenkov.filmscatalog.presentation.fragments.FavouriteFilmsListFragment
import com.vkochenkov.filmscatalog.presentation.fragments.FilmInfoFragment
import com.vkochenkov.filmscatalog.presentation.fragments.FilmsListFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: Repository

    lateinit var bottomNavView: BottomNavigationView

    val fragment1 = FilmsListFragment()
    val fragment2 = FavouriteFilmsListFragment()
    val fragment3 = AppInfoFragment()

    init {
        App.appComponent.inject(this)
    }

    companion object {
        const val FILM = "FILM"
        const val FILM_NAME = "FILM_NAME"
        const val BUNDLE = "BUNDLE"
    }

    //исполняется, если приложение открыто, или свернуто, и была открыта нотификация
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        checkIntentForOpenFilm(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFields()
        setBottomNavigationClickListener()

        if (supportFragmentManager.backStackEntryCount == 0) {
            replaceFragment(fragment1)
        }

        //проверяем, что бы не открывать снова фильм из интента при пересоздании активити
        if (savedInstanceState == null) {
            checkIntentForOpenFilm(intent)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        } else {
            showExitDialog()
        }
    }

    private fun checkIntentForOpenFilm(intent: Intent?) {
        //в бандл приходят данные из alarmManager
        val bundle = intent?.getBundleExtra(BUNDLE)
        //если пришел пуш из FCM, с названием фильма (отправляем именно название, а не айди, тк айди не статичный)
        val filmName = intent?.getStringExtra(FILM_NAME)
        if (bundle != null) {
            val film = bundle.getParcelable<Film>(FILM)
            if (film != null) {
                openFilmInfoFragment(bundle)
            }
        } else if (filmName != null) {
            repository.getFilm(filmName, object : Repository.GetFilmFromDatabaseCallback {
                override fun onSuccess(film: Film) {
                    val bundle = Bundle()
                    bundle.putParcelable(FILM, film)
                    openFilmInfoFragment(bundle)
                }
            })
        }
    }

    private fun openFilmInfoFragment(bundle: Bundle?) {
        val filmInfoFragment = FilmInfoFragment()
        filmInfoFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragments_container, filmInfoFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showExitDialog() {
        val exitDialog = ExitDialog(this, this)
        exitDialog.show()
    }

    private fun initFields() {
        bottomNavView = findViewById(R.id.bottom_nav_view)
    }

    private fun setBottomNavigationClickListener() {
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home_item -> {
                    replaceFragment(fragment1)
                }
                R.id.menu_favourites_item -> {
                    replaceFragment(fragment2)
                }
                R.id.menu_info_item -> {
                    replaceFragment(fragment3)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragments = supportFragmentManager.fragments
        val size = fragments.size

        if (size == 0 || fragments[size - 1]::class != fragment::class) {
            supportFragmentManager.popBackStack()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragments_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}