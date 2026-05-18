package com.example.bestreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.showMathReminder(context);
        ReminderScheduler.rescheduleIfEnabled(context);
    }
}
