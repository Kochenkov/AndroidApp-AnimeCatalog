package com.vkochenkov.filmscatalog.view.dialogs

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.receivers.FilmNotificationReceiver
import com.vkochenkov.filmscatalog.view.MainActivity.Companion.FILM
import com.vkochenkov.filmscatalog.viewmodel.NotificationViewModel
import java.util.*

class WatchLaterDialogFragment : DialogFragment() {

    var film: Film? = null
        set(value) {
            field = value
        }
    var viewModel: NotificationViewModel? = null
        set(value) {
            field = value
        }

    private lateinit var datePicker: DatePicker
    private lateinit var timePicker: TimePicker

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) {
            film = savedInstanceState.getParcelable("film")
            viewModel = savedInstanceState.getParcelable("viewModel")
        }
        val view = inflater.inflate(R.layout.dialog_fragment_watch_later, container, false)
        timePicker = view.findViewById(R.id.time_picker)
        datePicker = view.findViewById(R.id.date_picker)
        setOnBtnClickListeners(view)

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("film", film)
        outState.putParcelable("viewModel", viewModel)
    }

    private fun setOnBtnClickListeners(view: View) {
        view.findViewById<Button>(R.id.btn_create_notification).setOnClickListener {

            val alarmManager =
                activity!!.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

            val intent = Intent(activity, FilmNotificationReceiver::class.java)
            val bundle = Bundle()
            bundle.putParcelable(FILM, film)
            bundle.putInt("notificationId", 1)

            intent.putExtra("bundle", bundle)

            val alarmIntent =
                PendingIntent.getBroadcast(activity, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

            val year = datePicker.year
            val month = datePicker.month
            val day = datePicker.dayOfMonth
            val hour = timePicker.currentHour
            val minutes = timePicker.currentMinute

            val startTime = Calendar.getInstance()

            startTime.set(Calendar.YEAR, year)
            startTime.set(Calendar.MONTH, month)
            startTime.set(Calendar.DAY_OF_MONTH, day)
            startTime.set(Calendar.HOUR, hour)
            startTime.set(Calendar.MINUTE, minutes)
            startTime.set(Calendar.SECOND, 0)

            val alarmStartTime = startTime.timeInMillis

            //todo
            Log.d("notification", "alarm: " + startTime.timeInMillis.toString())
            Log.d("notification", "current millis: " + System.currentTimeMillis().toString())

            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent)

            //todo
            Toast.makeText(context, "message", Toast.LENGTH_LONG).show()
            dismiss()

            viewModel?.notifyFilm(film!!.serverName)
            viewModel?.isNotifyFilm(film!!.serverName)
        }

        view.findViewById<Button>(R.id.btn_cancel_create_notif).setOnClickListener {
            dismiss()
        }
    }
}