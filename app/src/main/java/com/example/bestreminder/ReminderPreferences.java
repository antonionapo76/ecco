package com.example.bestreminder;

import android.content.Context;
import android.content.SharedPreferences;

final class ReminderPreferences {
    static final int DEFAULT_HOUR = 9;
    static final int DEFAULT_MINUTE = 0;

    private static final String FILE_NAME = "best_reminder_preferences";
    private static final String KEY_ENABLED = "enabled";
    private static final String KEY_HOUR = "hour";
    private static final String KEY_MINUTE = "minute";

    private ReminderPreferences() {
    }

    static boolean isEnabled(Context context) {
        return preferences(context).getBoolean(KEY_ENABLED, false);
    }

    static int getHour(Context context) {
        return preferences(context).getInt(KEY_HOUR, DEFAULT_HOUR);
    }

    static int getMinute(Context context) {
        return preferences(context).getInt(KEY_MINUTE, DEFAULT_MINUTE);
    }

    static void save(Context context, boolean enabled, int hour, int minute) {
        preferences(context)
                .edit()
                .putBoolean(KEY_ENABLED, enabled)
                .putInt(KEY_HOUR, hour)
                .putInt(KEY_MINUTE, minute)
                .apply();
    }

    static void setEnabled(Context context, boolean enabled) {
        preferences(context).edit().putBoolean(KEY_ENABLED, enabled).apply();
    }

    private static SharedPreferences preferences(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }
}
