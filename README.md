# Best Reminder

Best Reminder is a simple native Android app that reminds you every day that you are the best.

## What it does

- Shows a clean one-screen setup flow
- Lets you choose your daily reminder time
- Schedules a repeating notification that says "You are the best."
- Reschedules the reminder after device reboot or app update

## Project layout

- `app/` - Android application module
- `app/src/main/java/com/example/bestreminder/` - activity, scheduler, and receivers
- `app/src/test/java/com/example/bestreminder/` - focused unit tests for reminder timing

## Open in Android Studio

1. Make sure you have the Android SDK installed locally.
2. Open this repository in Android Studio.
3. Let Gradle sync the project.
4. Run the app on an emulator or Android device.

## Notes

- On Android 13+, the app requests notification permission before enabling reminders.
- The reminder uses `AlarmManager.setInexactRepeating`, which is battery-friendly for a once-per-day notification.