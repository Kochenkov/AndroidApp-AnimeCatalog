package com.vkochenkov.filmscatalog.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.view.dialogs.ExitDialog
import com.vkochenkov.filmscatalog.view.fragments.AppInfoFragment
import com.vkochenkov.filmscatalog.view.fragments.FavouriteFilmsListFragment
import com.vkochenkov.filmscatalog.view.fragments.FilmInfoFragment
import com.vkochenkov.filmscatalog.view.fragments.FilmsListFragment

class MainActivity : AppCompatActivity() {

    var bundle: Bundle? = null

    lateinit var bottomNavView: BottomNavigationView

    val fragment1 = FilmsListFragment()
    val fragment2 = FavouriteFilmsListFragment()
    val fragment3 = AppInfoFragment()

    companion object {
        const val FILM = "FILM"
        const val BUNDLE = "BUNDLE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFields()
        setBottomNavigationClickListener()

        if (supportFragmentManager.backStackEntryCount == 0) {
            replaceFragment(fragment1)
        }

        // проверяем, впервые создана активити, или пересоздана (например при повороте экрана)
        if (savedInstanceState == null) {
            bundle = intent.getBundleExtra(BUNDLE)
        } else {
            bundle = null
        }
        val film = bundle?.getParcelable<Film>(FILM)
        // если фильм будет не пустой (попадаем сюда из ресивера по нотификации), то открываем фрагмент с фильмом
        if (film != null) {
            intent = null
            val filmInfoFragment = FilmInfoFragment()
            filmInfoFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragments_container, filmInfoFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    //сохраняем бандл при пересоздании активити, для блокирования многократного пересоздания фрагмента с фильмом
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelable(BUNDLE, bundle)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        } else {
            showExitDialog()
        }
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