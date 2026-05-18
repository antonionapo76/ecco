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
    private static final int ANSWER_REQUEST_CODE_BASE = 1300;

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

        MathChallenge challenge = MathChallenge.create();
        createNotificationChannel(notificationManager);
        notificationManager.notify(NOTIFICATION_ID, buildChallengeNotification(context, challenge));
        return true;
    }

    static boolean showAnswerResult(Context context, boolean isCorrect, int correctAnswer) {
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
        notificationManager.notify(NOTIFICATION_ID, buildResultNotification(context, isCorrect, correctAnswer));
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
        channel.setDescription("Math reminders that you are the best.");
        notificationManager.createNotificationChannel(channel);
    }

    @SuppressWarnings("deprecation")
    private static Notification buildChallengeNotification(Context context, MathChallenge challenge) {
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

        builder
                .setSmallIcon(R.drawable.ic_stat_best)
                .setContentTitle("I am the best!")
                .setContentText(challenge.question)
                .setStyle(new Notification.BigTextStyle()
                        .bigText("I am the best. Solve this: " + challenge.question))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setCategory(Notification.CATEGORY_REMINDER);

        for (int i = 0; i < challenge.answers.length; i++) {
            int answer = challenge.answers[i];
            builder.addAction(
                    R.drawable.ic_stat_best,
                    String.valueOf(answer),
                    createAnswerIntent(context, i, answer, challenge.correctAnswer)
            );
        }

        return builder.build();
    }

    @SuppressWarnings("deprecation")
    private static Notification buildResultNotification(Context context, boolean isCorrect, int correctAnswer) {
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

        String title = isCorrect ? "Congratulations!" : "Try again next time";
        String message = isCorrect
                ? "Correct answer. You are the best!"
                : "The correct answer was " + correctAnswer + ". You are still the best.";

        return builder
                .setSmallIcon(R.drawable.ic_stat_best)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new Notification.BigTextStyle().bigText(message))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setCategory(Notification.CATEGORY_REMINDER)
                .build();
    }

    private static PendingIntent createAnswerIntent(
            Context context,
            int answerIndex,
            int selectedAnswer,
            int correctAnswer
    ) {
        Intent answerIntent = new Intent(context, MathAnswerReceiver.class)
                .setAction(MathAnswerReceiver.ACTION_ANSWER)
                .putExtra(MathAnswerReceiver.EXTRA_SELECTED_ANSWER, selectedAnswer)
                .putExtra(MathAnswerReceiver.EXTRA_CORRECT_ANSWER, correctAnswer);

        return PendingIntent.getBroadcast(
                context,
                ANSWER_REQUEST_CODE_BASE + answerIndex,
                answerIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
    }
}
