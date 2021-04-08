package com.vkochenkov.filmscatalog.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.db.Film

class WatchLaterDialogFragment(val film: Film): DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_watch_later, container, false)

        setOnBtnClickListeners(view)

        return view
    }

    private fun setOnBtnClickListeners(view: View) {
        view.findViewById<Button>(R.id.btn_create_notification).setOnClickListener{
            //todo
            Toast.makeText(context, "message", Toast.LENGTH_LONG).show()
            dismiss()
        }

        view.findViewById<Button>(R.id.btn_cancel_create_notif).setOnClickListener{
            dismiss()
        }
    }
}