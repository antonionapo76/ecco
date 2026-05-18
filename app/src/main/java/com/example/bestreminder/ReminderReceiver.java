package com.example.bestreminder;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

public final class ReminderReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "daily_affirmations";
    private static final int NOTIFICATION_ID = 42;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!ReminderScheduler.ACTION_DAILY_REMINDER.equals(intent.getAction())) {
            return;
        }
        if (!ReminderPreferences.isEnabled(context) || !canPostNotifications(context)) {
            return;
        }

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return;
        }

        createNotificationChannel(notificationManager);
        notificationManager.notify(NOTIFICATION_ID, buildNotification(context));
    }

    static void createNotificationChannel(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            createNotificationChannel(notificationManager);
        }
    }

    static boolean canPostNotifications(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return true;
        }
        return context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED;
    }

    private static Notification buildNotification(Context context) {
        Intent launchIntent = new Intent(context, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(
                context,
                0,
                launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification.Builder builder = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                ? new Notification.Builder(context, CHANNEL_ID)
                : new Notification.Builder(context);

        return builder
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Daily reminder")
                .setContentText(context.getString(R.string.affirmation))
                .setStyle(new Notification.BigTextStyle().bigText(context.getString(R.string.affirmation)))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setShowWhen(true)
                .build();
    }

    private static void createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Daily affirmations",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription("A daily reminder that you are the best.");
        notificationManager.createNotificationChannel(channel);
    }
}
