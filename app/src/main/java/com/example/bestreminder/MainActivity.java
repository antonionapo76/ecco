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

public class MainActivity extends Activity {
    private static final int NOTIFICATION_PERMISSION_REQUEST = 42;

    private TextView statusText;
    private TimePicker timePicker;
    private boolean sendTestAfterPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReminderScheduler.ensureDefaultReminder(this);
        setContentView(createContentView());
        requestNotificationPermissionIfNeeded();
        updateStatusText();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST) {
            if (sendTestAfterPermission && isNotificationPermissionGranted()) {
                sendTestAfterPermission = false;
                sendTestNotification();
                return;
            }
            sendTestAfterPermission = false;
            updateStatusText();
        }
    }

    private LinearLayout createContentView() {
        int horizontalPadding = dp(24);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(horizontalPadding, dp(36), horizontalPadding, dp(24));
        root.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("I am the best.");
        title.setTextColor(Color.rgb(30, 64, 175));
        title.setTextSize(34);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(title.getTypeface(), android.graphics.Typeface.BOLD);
        root.addView(title, matchWrapLayout());

        TextView subtitle = new TextView(this);
        subtitle.setText("Your phone will remind you every day.");
        subtitle.setTextColor(Color.rgb(71, 85, 105));
        subtitle.setTextSize(18);
        subtitle.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams subtitleParams = matchWrapLayout();
        subtitleParams.setMargins(0, dp(12), 0, dp(32));
        root.addView(subtitle, subtitleParams);

        timePicker = new TimePicker(this);
        timePicker.setIs24HourView(false);
        timePicker.setHour(ReminderScheduler.getReminderHour(this));
        timePicker.setMinute(ReminderScheduler.getReminderMinute(this));
        root.addView(timePicker, matchWrapLayout());

        Button saveButton = new Button(this);
        saveButton.setText("Save daily reminder");
        saveButton.setAllCaps(false);
        saveButton.setTextSize(18);
        saveButton.setOnClickListener(view -> {
            ReminderScheduler.scheduleDailyReminder(this, timePicker.getHour(), timePicker.getMinute());
            requestNotificationPermissionIfNeeded();
            updateStatusText();
        });
        LinearLayout.LayoutParams buttonParams = matchWrapLayout();
        buttonParams.setMargins(0, dp(28), 0, dp(16));
        root.addView(saveButton, buttonParams);

        Button testButton = new Button(this);
        testButton.setText("Send test notification now");
        testButton.setAllCaps(false);
        testButton.setTextSize(18);
        testButton.setOnClickListener(view -> {
            if (!isNotificationPermissionGranted()) {
                sendTestAfterPermission = true;
                requestNotificationPermissionIfNeeded();
                updateStatusText();
                return;
            }
            sendTestNotification();
        });
        LinearLayout.LayoutParams testButtonParams = matchWrapLayout();
        testButtonParams.setMargins(0, 0, 0, dp(16));
        root.addView(testButton, testButtonParams);

        statusText = new TextView(this);
        statusText.setTextColor(Color.rgb(51, 65, 85));
        statusText.setTextSize(16);
        statusText.setGravity(Gravity.CENTER);
        root.addView(statusText, matchWrapLayout());

        return root;
    }

    private void updateStatusText() {
        if (statusText == null) {
            return;
        }

        String reminderTime = ReminderScheduler.formatTime(
                ReminderScheduler.getReminderHour(this),
                ReminderScheduler.getReminderMinute(this)
        );

        if (!isNotificationPermissionGranted()) {
            statusText.setText(String.format(
                    Locale.getDefault(),
                    "Reminder set for %s. Allow notifications so the reminder can appear.",
                    reminderTime
            ));
            return;
        }

        statusText.setText(String.format(
                Locale.getDefault(),
                "Reminder set for %s every day.",
                reminderTime
        ));
    }

    private void sendTestNotification() {
        if (NotificationHelper.showDailyAffirmation(this)) {
            statusText.setText("Test notification sent. If you do not see it, check notification settings for Best Reminder.");
        } else {
            updateStatusText();
        }
    }

    private void requestNotificationPermissionIfNeeded() {
        if (!isNotificationPermissionGranted()) {
            requestPermissions(
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    NOTIFICATION_PERMISSION_REQUEST
            );
        }
    }

    private boolean isNotificationPermissionGranted() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
                || checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
    }

    private LinearLayout.LayoutParams matchWrapLayout() {
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
