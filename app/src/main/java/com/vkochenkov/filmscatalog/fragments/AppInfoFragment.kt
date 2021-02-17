package com.vkochenkov.filmscatalog.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.vkochenkov.filmscatalog.R

class AppInfoFragment : Fragment() {

    private lateinit var mainToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_app_info, container, false)

        mainToolbar = (activity as AppCompatActivity).findViewById(R.id.main_toolbar)
        setOnClickListenerForShareBtn(view)

        return view
    }

    override fun onResume() {
        super.onResume()
        mainToolbar.setTitle(R.string.info_title_text)
    }

    private fun setOnClickListenerForShareBtn(view: View?) {
        view?.findViewById<Button>(R.id.btn_share)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
            }
            startActivity(Intent.createChooser(intent, null))
        }
    }
}