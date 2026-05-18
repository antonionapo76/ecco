package com.youarethebest.app.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val prefs = context.getSharedPreferences("you_are_the_best_prefs", Context.MODE_PRIVATE)
            val enabled = prefs.getBoolean("reminders_enabled", true)
            val hour = prefs.getInt("reminder_hour", 9)
            val minute = prefs.getInt("reminder_minute", 0)
            if (enabled) {
                ReminderScheduler.scheduleDaily(context, hour, minute)
            }
        }
    }
}
