package com.example.bestreminder;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Locale;

public final class MainActivity extends Activity {
    private static final int REQUEST_POST_NOTIFICATIONS = 7;

    private TimePicker timePicker;
    private TextView statusText;
    private boolean waitingToEnableReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReminderReceiver.createNotificationChannel(this);
        setContentView(createContentView());
        updateStatus();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != REQUEST_POST_NOTIFICATIONS || !waitingToEnableReminder) {
            return;
        }

        waitingToEnableReminder = false;
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            saveAndScheduleReminder();
        } else {
            ReminderPreferences.setEnabled(this, false);
            updateStatus("Notifications are off. Allow notifications to receive the daily reminder.");
        }
    }

    private LinearLayout createContentView() {
        int padding = dp(24);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(padding, padding, padding, padding);
        root.setBackgroundColor(Color.WHITE);
        root.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        TextView title = new TextView(this);
        title.setText(getString(R.string.affirmation));
        title.setTextSize(36);
        title.setTextColor(Color.rgb(36, 36, 48));
        title.setGravity(Gravity.CENTER);
        title.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        root.addView(title, matchWidthWrapHeight());

        TextView subtitle = new TextView(this);
        subtitle.setText("Choose a time, then save your daily reminder.");
        subtitle.setTextSize(16);
        subtitle.setTextColor(Color.rgb(90, 90, 105));
        subtitle.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams subtitleParams = matchWidthWrapHeight();
        subtitleParams.setMargins(0, dp(12), 0, dp(20));
        root.addView(subtitle, subtitleParams);

        timePicker = new TimePicker(this);
        timePicker.setIs24HourView(false);
        setPickerTime(ReminderPreferences.getHour(this), ReminderPreferences.getMinute(this));
        root.addView(timePicker, matchWidthWrapHeight());

        Button saveButton = new Button(this);
        saveButton.setText("Save daily reminder");
        saveButton.setOnClickListener(view -> enableReminder());
        LinearLayout.LayoutParams buttonParams = matchWidthWrapHeight();
        buttonParams.setMargins(0, dp(20), 0, 0);
        root.addView(saveButton, buttonParams);

        Button cancelButton = new Button(this);
        cancelButton.setText("Turn off reminder");
        cancelButton.setOnClickListener(view -> disableReminder());
        LinearLayout.LayoutParams cancelParams = matchWidthWrapHeight();
        cancelParams.setMargins(0, dp(8), 0, 0);
        root.addView(cancelButton, cancelParams);

        statusText = new TextView(this);
        statusText.setTextSize(14);
        statusText.setTextColor(Color.rgb(90, 90, 105));
        statusText.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams statusParams = matchWidthWrapHeight();
        statusParams.setMargins(0, dp(18), 0, 0);
        root.addView(statusText, statusParams);

        return root;
    }

    private void enableReminder() {
        if (!ReminderReceiver.canPostNotifications(this)) {
            waitingToEnableReminder = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissions(
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_POST_NOTIFICATIONS
                );
            }
            return;
        }
        saveAndScheduleReminder();
    }

    private void saveAndScheduleReminder() {
        int hour = getPickerHour();
        int minute = getPickerMinute();
        ReminderPreferences.save(this, true, hour, minute);
        ReminderScheduler.schedule(this, hour, minute);
        updateStatus();
    }

    private void disableReminder() {
        ReminderPreferences.setEnabled(this, false);
        ReminderScheduler.cancel(this);
        updateStatus("Daily reminder is off.");
    }

    private void updateStatus() {
        if (!ReminderPreferences.isEnabled(this)) {
            updateStatus("Daily reminder is off.");
            return;
        }
        updateStatus(String.format(
                Locale.getDefault(),
                "Daily reminder is on for %s.",
                formatTime(ReminderPreferences.getHour(this), ReminderPreferences.getMinute(this))
        ));
    }

    private void updateStatus(String message) {
        if (statusText != null) {
            statusText.setText(message);
        }
    }

    private void setPickerTime(int hour, int minute) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
        } else {
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);
        }
    }

    private int getPickerHour() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return timePicker.getHour();
        }
        return timePicker.getCurrentHour();
    }

    private int getPickerMinute() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return timePicker.getMinute();
        }
        return timePicker.getCurrentMinute();
    }

    private String formatTime(int hour, int minute) {
        int displayHour = hour % 12;
        if (displayHour == 0) {
            displayHour = 12;
        }
        String suffix = hour >= 12 ? "PM" : "AM";
        return String.format(Locale.getDefault(), "%d:%02d %s", displayHour, minute, suffix);
    }

    private LinearLayout.LayoutParams matchWidthWrapHeight() {
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
