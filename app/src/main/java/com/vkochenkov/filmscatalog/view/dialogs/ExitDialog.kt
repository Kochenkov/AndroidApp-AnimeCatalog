package com.vkochenkov.filmscatalog.view.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import com.vkochenkov.filmscatalog.R

class ExitDialog(context: Context, private val activity: Activity) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_custom_exit)

        findViewById<Button>(R.id.dialog_exit_btn).setOnClickListener {
            activity.finish()
        }
        findViewById<Button>(R.id.dialog_no_exit_btn).setOnClickListener {
            dismiss()
        }
    }
}