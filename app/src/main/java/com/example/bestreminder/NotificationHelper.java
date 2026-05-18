package com.example.bestreminder;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

final class NotificationHelper {
    private static final String CHANNEL_ID = "daily_affirmation";
    private static final int NOTIFICATION_ID = 1208;

    private NotificationHelper() {
    }

    static boolean showDailyAffirmation(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return false;
        }

        createNotificationChannel(notificationManager);
        notificationManager.notify(NOTIFICATION_ID, buildNotification(context));
        return true;
    }

    private static void createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Daily affirmation",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription("Daily reminders that you are the best.");
        notificationManager.createNotificationChannel(channel);
    }

    @SuppressWarnings("deprecation")
    private static Notification buildNotification(Context context) {
        Intent launchIntent = new Intent(context, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(
                context,
                0,
                launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(context, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(context);
        }

        return builder
                .setSmallIcon(R.drawable.ic_stat_best)
                .setContentTitle("Daily reminder")
                .setContentText(context.getString(R.string.affirmation))
                .setStyle(new Notification.BigTextStyle()
                        .bigText("I am the best. Keep going today."))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setCategory(Notification.CATEGORY_REMINDER)
                .build();
    }
}
