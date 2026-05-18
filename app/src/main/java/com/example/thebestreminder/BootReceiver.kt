package com.example.thebestreminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val settings = ReminderPreferences(context).loadSettings()
        if (!settings.enabled) {
            return
        }

        ReminderScheduler(context).scheduleReminder(settings.hour, settings.minute)
    }
}
