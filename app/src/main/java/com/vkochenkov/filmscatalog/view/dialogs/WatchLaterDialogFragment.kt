package com.vkochenkov.filmscatalog.view.dialogs

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
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
import com.vkochenkov.filmscatalog.receivers.FilmNotificationReceiver.Companion.NOTIFICATION_ID
import com.vkochenkov.filmscatalog.view.MainActivity.Companion.BUNDLE
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_watch_later, container, false)

        datePicker = view.findViewById(R.id.date_picker)
        datePicker.minDate = Calendar.getInstance().timeInMillis
        timePicker = view.findViewById(R.id.time_picker)
        timePicker.setIs24HourView(true)

        setOnBtnClickListeners(view)

        return view
    }

    private fun setOnBtnClickListeners(view: View) {
        view.findViewById<Button>(R.id.btn_create_notification).setOnClickListener {

            val alarmManager =
                activity!!.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

            //create intent for broadcast receiver
            val intent = Intent(activity, FilmNotificationReceiver::class.java)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
            //create bundle and set data to it
            val bundle = Bundle()
            bundle.putParcelable(FILM, film)
            val id = createNotificationId(film!!.serverName)
            bundle.putInt(NOTIFICATION_ID, id)
            intent.putExtra(BUNDLE, bundle)
            //create different action for receive all notifications
            intent.action = id.toString()
            //create pending intent for alarm manager
            val alarmIntent = PendingIntent.getBroadcast(activity, id, intent, 0)

            //get date from pickers
            val year = datePicker.year
            val month = datePicker.month
            val day = datePicker.dayOfMonth
            val hour: Int?
            val minutes: Int?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hour = timePicker.hour
                minutes = timePicker.minute
            } else {
                hour = timePicker.currentHour
                minutes = timePicker.currentMinute
            }

            //create instance of calendar and set date to it
            val startTime = Calendar.getInstance()
            startTime.set(Calendar.YEAR, year)
            startTime.set(Calendar.MONTH, month)
            startTime.set(Calendar.DAY_OF_MONTH, day)
            startTime.set(Calendar.HOUR_OF_DAY, hour)
            startTime.set(Calendar.MINUTE, minutes)
            startTime.set(Calendar.SECOND, 0)

            val alarmStartTime = startTime.timeInMillis

            //set alarm manager for open broadcast receiver after a time
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent)

            viewModel?.setNotificationFilm(film!!.serverName, alarmStartTime)
            viewModel?.isNotifyFilm(film!!.serverName)

            Toast.makeText(
                context,
                context?.getText(R.string.notification_created_str),
                Toast.LENGTH_LONG
            ).show()
            dismiss()
        }

        view.findViewById<Button>(R.id.btn_cancel_create_notif).setOnClickListener {
            dismiss()
        }
    }

    private fun createNotificationId(filmName: String): Int {
        return filmName.hashCode()
    }
}