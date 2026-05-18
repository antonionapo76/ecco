package com.example.bestreminder.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "reminder_prefs")

data class ReminderTime(val hour: Int, val minute: Int)

class ReminderPrefs(private val context: Context) {

    val reminderTime: Flow<ReminderTime> = context.dataStore.data.map { prefs ->
        ReminderTime(
            hour = prefs[KEY_HOUR] ?: DEFAULT_HOUR,
            minute = prefs[KEY_MINUTE] ?: DEFAULT_MINUTE
        )
    }

    suspend fun setReminderTime(hour: Int, minute: Int) {
        context.dataStore.edit { prefs ->
            prefs[KEY_HOUR] = hour
            prefs[KEY_MINUTE] = minute
        }
    }

    companion object {
        private val KEY_HOUR = intPreferencesKey("reminder_hour")
        private val KEY_MINUTE = intPreferencesKey("reminder_minute")
        const val DEFAULT_HOUR = 9
        const val DEFAULT_MINUTE = 0
    }
}
