package com.vkochenkov.filmscatalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vkochenkov.filmscatalog.dialogs.ExitDialog
import com.vkochenkov.filmscatalog.fragments.FilmsListFragment

class MainActivity : AppCompatActivity() {

    companion object {
        const val FILM = "FILM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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