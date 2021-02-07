package com.vkochenkov.filmscatalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vkochenkov.filmscatalog.dialogs.ExitDialog
import com.vkochenkov.filmscatalog.fragments.FavouriteFilmsListFragment
import com.vkochenkov.filmscatalog.fragments.FilmsListFragment

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavView: BottomNavigationView

    companion object {
        const val FILM = "FILM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavView = findViewById(R.id.bottom_nav_view)
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home_item -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments_container, FilmsListFragment())
                        .commit()
                }
                R.id.menu_favourites_item -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments_container, FavouriteFilmsListFragment())
                        .commit()
                }
                R.id.menu_info_item -> {
                    //todo create new fragment
                }
            }
            true
        }


        if (supportFragmentManager.backStackEntryCount == 0) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragments_container, FilmsListFragment())
                .commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            showExitDialog()
        }
    }

    private fun showExitDialog() {
        val exitDialog = ExitDialog(this, this)
        exitDialog.show()
    }
}