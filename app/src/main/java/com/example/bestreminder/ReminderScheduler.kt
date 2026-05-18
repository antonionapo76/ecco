package com.example.bestreminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.time.ZonedDateTime

object ReminderScheduler {
    private const val preferencesName = "best_reminder_preferences"
    private const val keyEnabled = "key_enabled"
    private const val keyHour = "key_hour"
    private const val keyMinute = "key_minute"

    const val defaultHour = 9
    const val defaultMinute = 0

    fun scheduleReminder(context: Context, hour: Int, minute: Int) {
        saveReminderSettings(context, enabled = true, hour = hour, minute = minute)
        ReminderReceiver.ensureNotificationChannel(context)

        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val pendingIntent = reminderPendingIntent(context)
        val triggerAtMillis = nextTriggerMillis(ZonedDateTime.now(), hour, minute)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelReminder(context: Context) {
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        alarmManager.cancel(reminderPendingIntent(context))
        saveReminderSettings(context, enabled = false)
    }

    fun rescheduleIfEnabled(context: Context) {
        if (!isReminderEnabled(context)) {
            return
        }

        val (hour, minute) = loadReminderTime(context)
        scheduleReminder(context, hour, minute)
    }

    fun saveReminderTime(context: Context, hour: Int, minute: Int) {
        saveReminderSettings(
            context,
            enabled = isReminderEnabled(context),
            hour = hour,
            minute = minute
        )
    }

    fun isReminderEnabled(context: Context): Boolean =
        preferences(context).getBoolean(keyEnabled, false)

    fun loadReminderTime(context: Context): Pair<Int, Int> {
        val preferences = preferences(context)
        val hour = preferences.getInt(keyHour, defaultHour)
        val minute = preferences.getInt(keyMinute, defaultMinute)
        return hour to minute
    }

    internal fun nextTriggerMillis(now: ZonedDateTime, hour: Int, minute: Int): Long {
        var nextTrigger = now
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)

        if (!nextTrigger.isAfter(now)) {
            nextTrigger = nextTrigger.plusDays(1)
        }

        return nextTrigger.toInstant().toEpochMilli()
    }

    private fun reminderPendingIntent(context: Context): PendingIntent {
        val reminderIntent = Intent(context, ReminderReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            1001,
            reminderIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun saveReminderSettings(
        context: Context,
        enabled: Boolean,
        hour: Int? = null,
        minute: Int? = null
    ) {
        preferences(context).edit().apply {
            putBoolean(keyEnabled, enabled)
            if (hour != null) {
                putInt(keyHour, hour)
            }
            if (minute != null) {
                putInt(keyMinute, minute)
            }
            apply()
        }
    }

    private fun preferences(context: Context) =
        context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
}
