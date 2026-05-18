package com.example.bestreminder.notifications

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.bestreminder.MainActivity
import com.example.bestreminder.R

object ReminderNotifier {
    const val CHANNEL_ID = "daily_best_reminder"
    private const val NOTIFICATION_ID = 1001

    private val affirmations = listOf(
        "You are the best. Today is yours.",
        "You are the best — keep shining.",
        "Reminder: you are the best, no contest.",
        "You are the best. Carry that with you today.",
        "You are the best. Believe it because it's true.",
        "Hey champion — you are the best.",
        "You are the best. Yesterday, today, tomorrow.",
        "You are the best. The world is lucky to have you."
    )

    fun show(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val message = affirmations.random()

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val granted = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        if (granted) {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
        }
    }
}
