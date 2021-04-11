package com.vkochenkov.filmscatalog.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_DEFAULT
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.db.Film
import com.vkochenkov.filmscatalog.view.MainActivity

class FilmNotificationReceiver : BroadcastReceiver() {

    companion object {
        val CHANNEL_ID = "SAMPLE_CHANNEL"
    }

    override fun onReceive(context: Context, intent: Intent) {

        Log.d("notification", "onReceive")

        val bundle = intent.getBundleExtra("bundle")
        val notificationId = bundle?.getInt("notificationId")
        val film = bundle?.getParcelable<Film>("film")

        //todo заменить на открытие соответствующего фрагмента
        val intentActivity = Intent(context, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(context, 0, intentActivity, 0)

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //for api 26 and above
            val channelName = "My Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, channelName, importance)

            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setChannelId(CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_totoro)
            .setContentTitle("Напоминание о просмотре")
            .setContentText(film?.title)
            .setContentIntent(contentIntent)
            .setPriority(PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId!!, notification)
    }
}