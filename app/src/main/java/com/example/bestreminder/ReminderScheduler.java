package com.example.bestreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

final class ReminderScheduler {
    static final String ACTION_DAILY_REMINDER = "com.example.bestreminder.ACTION_DAILY_REMINDER";

    private static final int REQUEST_CODE = 1001;

    private ReminderScheduler() {
    }

    static void schedule(Context context, int hour, int minute) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            return;
        }

        PendingIntent pendingIntent = createPendingIntent(context);
        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                nextTriggerAt(hour, minute),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
    }

    static void cancel(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(createPendingIntent(context));
        }
    }

    private static PendingIntent createPendingIntent(Context context) {
        Intent intent = new Intent(context, ReminderReceiver.class)
                .setAction(ACTION_DAILY_REMINDER);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
        return PendingIntent.getBroadcast(context, REQUEST_CODE, intent, flags);
    }

    private static long nextTriggerAt(int hour, int minute) {
        Calendar next = Calendar.getInstance();
        next.set(Calendar.HOUR_OF_DAY, hour);
        next.set(Calendar.MINUTE, minute);
        next.set(Calendar.SECOND, 0);
        next.set(Calendar.MILLISECOND, 0);

        if (next.getTimeInMillis() <= System.currentTimeMillis()) {
            next.add(Calendar.DAY_OF_YEAR, 1);
        }

        return next.getTimeInMillis();
    }
}
