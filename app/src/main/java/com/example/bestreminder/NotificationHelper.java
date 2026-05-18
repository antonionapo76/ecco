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
    private static final String CHANNEL_ID = "math_challenges";
    private static final int NOTIFICATION_ID = 1208;

    private NotificationHelper() {
    }

    static boolean showMathReminder(Context context) {
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

        MathChallenge challenge = MathChallenge.create();
        createNotificationChannel(notificationManager);
        notificationManager.notify(NOTIFICATION_ID, buildChallengeNotification(context, challenge));
        return true;
    }

    private static void createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Math challenges",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription("Scheduled math challenge reminders.");
        notificationManager.createNotificationChannel(channel);
    }

    @SuppressWarnings("deprecation")
    private static Notification buildChallengeNotification(Context context, MathChallenge challenge) {
        Intent launchIntent = new Intent(context, ChallengeActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra(ChallengeActivity.EXTRA_QUESTION, challenge.question)
                .putExtra(ChallengeActivity.EXTRA_CORRECT_ANSWER, challenge.correctAnswer)
                .putExtra(ChallengeActivity.EXTRA_ANSWERS, challenge.answers);
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

        builder
                .setSmallIcon(R.drawable.ic_stat_best)
                .setContentTitle("Math challenge ready")
                .setContentText("Tap to answer the question.")
                .setStyle(new Notification.BigTextStyle()
                        .bigText("Tap this notification to open your math question."))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setCategory(Notification.CATEGORY_REMINDER);

        return builder.build();
    }
}
