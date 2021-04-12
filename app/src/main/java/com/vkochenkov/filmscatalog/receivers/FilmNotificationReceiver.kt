package com.vkochenkov.filmscatalog.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_DEFAULT
import com.vkochenkov.filmscatalog.App
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.Repository
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.view.MainActivity
import com.vkochenkov.filmscatalog.view.MainActivity.Companion.BUNDLE
import com.vkochenkov.filmscatalog.view.MainActivity.Companion.FILM

class FilmNotificationReceiver : BroadcastReceiver() {

    companion object {
        val CHANNEL_ID = "SAMPLE_CHANNEL"
        val NOTIFICATION_ID = "NOTIFICATION_ID"
    }

    override fun onReceive(context: Context, intent: Intent) {

        val bundle = intent.getBundleExtra(BUNDLE)
        val notificationId = bundle?.getInt(NOTIFICATION_ID)
        val film = bundle?.getParcelable<Film>(FILM)

        val intentActivity = Intent(context, MainActivity::class.java)
        intentActivity.putExtra(BUNDLE, bundle)
        //set unique request code for exact film
        val contentIntent =
            PendingIntent.getActivity(context, notificationId!!.toInt(), intentActivity, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //for api 26 and above
            val channelName = "My Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, channelName, importance)

            notificationManager.createNotificationChannel(channel)
        }

        val repository: Repository = App.instance!!.repository
        repository.unnotifyFilm(film!!.serverName)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setChannelId(CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_totoro)
                //todo вынести в строки
            .setContentTitle("Напоминание о просмотре")
            .setContentText(film.title)
            .setContentIntent(contentIntent)
            .setPriority(PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId!!, notification)
    }
}