package com.rinnestudio.testapplicationpecode

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap


const val CHANNEL_ID = "Notification channel id"
const val NOTIFICATION_INTENT_NAME = "Notification name"

class NotificationManager {

    fun createNotification(activity: Activity, position: Int) {
        val pendingIntent = createPendingIntent(activity, position)
        val icon = ResourcesCompat.getDrawable(
            activity.resources,
            R.drawable.ic_notification,
            activity.theme
        )?.toBitmap()
        val notification = NotificationCompat.Builder(activity.applicationContext, CHANNEL_ID)
            .setContentTitle("You create a notification")
            .setContentText("Notification ${position + 1}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_notification)
            .setLargeIcon(icon)
            .setOnlyAlertOnce(true)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(activity.applicationContext)) {
            notify(position, notification.build())
        }
    }


    private fun createPendingIntent(activity: Activity, position: Int): PendingIntent {
        val intent = Intent(activity.applicationContext, MainActivity::class.java)
        intent.putExtra(NOTIFICATION_INTENT_NAME, position)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        return PendingIntent.getActivity(
            activity,
            position,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    fun removeNotification(context: Context, position: Int) {
        NotificationManagerCompat.from(context).cancel(position)
    }

    fun createNotificationChannel(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    "Notification channel name",
                    NotificationManager.IMPORTANCE_HIGH
                )
            channel.description = "Notification"

            val notificationManager =
                activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}

