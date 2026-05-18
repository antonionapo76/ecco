package com.example.bestreminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

object ReminderScheduler {
    private const val PREFERENCE_FILE = "best_reminder_preferences"
    private const val KEY_REMINDER_ENABLED = "daily_reminder_enabled"

    fun scheduleDailyReminder(context: Context, hour: Int = 9, minute: Int = 0) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = reminderPendingIntent(context)

        val triggerAt = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAt.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        setReminderEnabled(context, true)
    }

    fun isReminderEnabled(context: Context): Boolean {
        return context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
            .getBoolean(KEY_REMINDER_ENABLED, false)
    }

    private fun setReminderEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_REMINDER_ENABLED, enabled)
            .apply()
    }

    private fun reminderPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, ReminderReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            1001,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
