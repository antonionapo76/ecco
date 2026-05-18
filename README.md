# Best Reminder

Best Reminder is a small native Android app that reminds you every day:

> I am the best.

The app schedules a daily Android notification, lets you pick the reminder time, and restores the reminder after the phone reboots.

## Open or build

1. Open this folder in Android Studio.
2. Let Android Studio sync the Gradle project.
3. Run the `app` configuration on an emulator or Android device.

You can also build from a machine with the Android SDK installed:

```sh
./gradlew assembleDebug
```

The project uses only platform Android APIs, so there are no third-party runtime dependencies.

## Behavior

- A default daily reminder is scheduled for 9:00 AM the first time the app opens.
- The time picker lets you save a different daily reminder time.
- The test button sends a notification immediately so you can confirm phone permissions are correct.
- Android 13 and newer require notification permission; the app requests it when opened.
- Android may still adjust background reminders to save battery, so the daily reminder can arrive near the selected time rather than exactly on the minute.