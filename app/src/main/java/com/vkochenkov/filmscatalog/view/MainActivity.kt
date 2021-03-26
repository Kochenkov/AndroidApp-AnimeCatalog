package com.vkochenkov.filmscatalog.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.view.dialogs.ExitDialog
import com.vkochenkov.filmscatalog.view.fragments.AppInfoFragment
import com.vkochenkov.filmscatalog.view.fragments.FavouriteFilmsListFragment
import com.vkochenkov.filmscatalog.view.fragments.FilmsListFragment

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavView: BottomNavigationView

    val fragment1 = FilmsListFragment()
    val fragment2 = FavouriteFilmsListFragment()
    val fragment3 = AppInfoFragment()

    companion object {
        const val FILM = "FILM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFields()
        setBottomNavigationClickListener()

        if (supportFragmentManager.backStackEntryCount == 0) {
            replaceFragment(fragment1)
        }
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

        if (size==0 || fragments[size-1]::class != fragment::class) {
            supportFragmentManager.popBackStack()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragments_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}