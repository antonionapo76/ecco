package com.example.thebestreminder

import android.content.Context

data class ReminderSettings(
    val hour: Int,
    val minute: Int,
    val enabled: Boolean,
)

class ReminderPreferences(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun loadSettings(): ReminderSettings =
        ReminderSettings(
            hour = preferences.getInt(KEY_HOUR, DEFAULT_HOUR),
            minute = preferences.getInt(KEY_MINUTE, DEFAULT_MINUTE),
            enabled = preferences.getBoolean(KEY_ENABLED, false),
        )

    fun saveTime(hour: Int, minute: Int) {
        preferences.edit()
            .putInt(KEY_HOUR, hour)
            .putInt(KEY_MINUTE, minute)
            .apply()
    }

    fun setEnabled(enabled: Boolean) {
        preferences.edit()
            .putBoolean(KEY_ENABLED, enabled)
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "the_best_reminder_prefs"
        private const val KEY_HOUR = "reminder_hour"
        private const val KEY_MINUTE = "reminder_minute"
        private const val KEY_ENABLED = "reminder_enabled"

        private const val DEFAULT_HOUR = 9
        private const val DEFAULT_MINUTE = 0
    }
}
