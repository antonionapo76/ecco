package com.example.bestreminder.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.bestreminder.data.ReminderPrefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        if (action != Intent.ACTION_BOOT_COMPLETED &&
            action != Intent.ACTION_MY_PACKAGE_REPLACED
        ) return

        val pendingResult = goAsync()
        val appContext = context.applicationContext
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val prefs = ReminderPrefs(appContext)
                val time = prefs.reminderTime.first()
                ReminderScheduler.schedule(appContext, time.hour, time.minute)
            } finally {
                pendingResult.finish()
            }
        }
    }
}
