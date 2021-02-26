package com.vkochenkov.filmscatalog.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.data.api.pojo.ResponseFromApi
import com.vkochenkov.filmscatalog.presentation.view.dialogs.ExitDialog
import com.vkochenkov.filmscatalog.presentation.view.fragments.AppInfoFragment
import com.vkochenkov.filmscatalog.presentation.view.fragments.FavouriteFilmsListFragment
import com.vkochenkov.filmscatalog.presentation.view.fragments.FilmsListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavView: BottomNavigationView

    companion object {
        const val FILM = "FILM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFields()
        setBottomNavigationClickListener()

        if (supportFragmentManager.backStackEntryCount == 0) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragments_container, FilmsListFragment())
                .addToBackStack("FilmsListFragment")
                .commit()
        }

        //todo
        //for test
        App.instance?.apiService?.getAnimeList()?.enqueue(object : Callback<ResponseFromApi> {
            override fun onResponse(
                call: Call<ResponseFromApi>,
                response: Response<ResponseFromApi>
            ) {
                Log.d("fromapi", response.body()?.data?.get(0)?.attributes?.title.toString())
                //todo
            }

            override fun onFailure(call: Call<ResponseFromApi>, t: Throwable) {
                Log.d("fromapi", t.message!!)

                //todo
            }
        })
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
            supportFragmentManager.popBackStack()
            when (item.itemId) {
                R.id.menu_home_item -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments_container, FilmsListFragment())
                        .addToBackStack("FilmsListFragment")
                        .commit()
                }
                R.id.menu_favourites_item -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments_container, FavouriteFilmsListFragment())
                        .addToBackStack("FavouriteFilmsListFragment")
                        .commit()
                }
                R.id.menu_info_item -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments_container, AppInfoFragment())
                        .addToBackStack("InfoFragment")
                        .commit()
                }
            }
            true
        }
    }
}