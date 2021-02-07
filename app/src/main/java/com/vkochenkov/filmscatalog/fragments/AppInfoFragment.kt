package com.vkochenkov.filmscatalog.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.vkochenkov.filmscatalog.R


class AppInfoFragment : Fragment() {

    lateinit var shareBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_app_info, container, false)

        setOnClickListenerForShareBtn(view)

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<View>(R.id.bottom_nav_view).visibility = View.VISIBLE
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