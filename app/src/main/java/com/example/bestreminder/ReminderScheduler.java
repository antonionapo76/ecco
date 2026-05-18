package com.example.bestreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Locale;

final class ReminderScheduler {
    static final int DEFAULT_HOUR = 9;
    static final int DEFAULT_MINUTE = 0;

    private static final String ACTION_SHOW_REMINDER = "com.example.bestreminder.SHOW_REMINDER";
    private static final String PREFS_NAME = "best_reminder";
    private static final String KEY_ENABLED = "enabled";
    private static final String KEY_HOUR = "hour";
    private static final String KEY_MINUTE = "minute";
    private static final int REQUEST_CODE = 1207;
    private static final int OPEN_APP_REQUEST_CODE = 1209;

    private ReminderScheduler() {
    }

    static void ensureDefaultReminder(Context context) {
        if (!isReminderSet(context)) {
            scheduleDailyReminder(context, DEFAULT_HOUR, DEFAULT_MINUTE);
        }
    }

    static void scheduleDailyReminder(Context context, int hourOfDay, int minute) {
        getPreferences(context)
                .edit()
                .putBoolean(KEY_ENABLED, true)
                .putInt(KEY_HOUR, hourOfDay)
                .putInt(KEY_MINUTE, minute)
                .apply();

        scheduleNextReminder(context, hourOfDay, minute);
    }

    static void rescheduleIfEnabled(Context context) {
        if (isReminderSet(context)) {
            scheduleNextReminder(context, getReminderHour(context), getReminderMinute(context));
        }
    }

    static boolean isReminderSet(Context context) {
        return getPreferences(context).getBoolean(KEY_ENABLED, false);
    }

    static int getReminderHour(Context context) {
        return getPreferences(context).getInt(KEY_HOUR, DEFAULT_HOUR);
    }

    static int getReminderMinute(Context context) {
        return getPreferences(context).getInt(KEY_MINUTE, DEFAULT_MINUTE);
    }

    static String formatTime(int hourOfDay, int minute) {
        boolean isAfternoon = hourOfDay >= 12;
        int hour12 = hourOfDay % 12;
        if (hour12 == 0) {
            hour12 = 12;
        }
        return String.format(Locale.getDefault(), "%d:%02d %s", hour12, minute, isAfternoon ? "PM" : "AM");
    }

    private static void scheduleNextReminder(Context context, int hourOfDay, int minute) {
        Intent intent = new Intent(context, ReminderReceiver.class)
                .setAction(ACTION_SHOW_REMINDER);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            long triggerAtMillis = nextTriggerMillis(hourOfDay, minute);
            alarmManager.setAlarmClock(
                    new AlarmManager.AlarmClockInfo(triggerAtMillis, createOpenAppIntent(context)),
                    pendingIntent
            );
        }
    }

    private static PendingIntent createOpenAppIntent(Context context) {
        Intent openAppIntent = new Intent(context, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(
                context,
                OPEN_APP_REQUEST_CODE,
                openAppIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
    }

    private static long nextTriggerMillis(int hourOfDay, int minute) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(java.util.Calendar.MINUTE, minute);
        calendar.set(java.util.Calendar.SECOND, 0);
        calendar.set(java.util.Calendar.MILLISECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(java.util.Calendar.DAY_OF_YEAR, 1);
        }

        return calendar.getTimeInMillis();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}
