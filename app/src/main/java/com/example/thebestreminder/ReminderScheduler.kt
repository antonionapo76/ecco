package com.example.thebestreminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class ReminderScheduler(private val context: Context) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    private val preferences = ReminderPreferences(context)

    fun scheduleReminder(hour: Int, minute: Int) {
        preferences.saveTime(hour, minute)
        preferences.setEnabled(true)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            ReminderTimeCalculator.nextTriggerAtMillis(hour, minute),
            AlarmManager.INTERVAL_DAY,
            reminderPendingIntent(context),
        )
    }

    fun cancelReminder() {
        alarmManager.cancel(reminderPendingIntent(context))
        preferences.setEnabled(false)
    }

    companion object {
        const val CHANNEL_ID = "best-reminder-daily"
        private const val REQUEST_CODE = 1001

        fun reminderPendingIntent(context: Context): PendingIntent {
            val intent = Intent(context, ReminderReceiver::class.java)
            return PendingIntent.getBroadcast(
                context,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        }
    }
}
