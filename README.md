# Math Reminder

Math Reminder is a small native Android app that schedules arithmetic questions at your chosen time and interval.

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

- A default reminder is scheduled for 9:00 AM the first time the app opens.
- The time picker lets you choose the first reminder time.
- If the selected first reminder time has already passed today, the first question is scheduled after the selected interval instead of tomorrow.
- After each reminder, the app repeats every 5 minutes, 10 minutes, 30 minutes, or 1 hour.
- Each reminder opens an addition, subtraction, multiplication, or division question.
- Tapping the notification opens a full app screen with three answer buttons. Tapping the right answer shows "Congratulations!"
- The test button sends a math notification immediately so you can confirm phone permissions are correct.
- Android 13 and newer require notification permission; the app requests it when opened.
- Reminders use Android's exact alarm-clock scheduling so they can appear at the selected minute.
- Because the reminder is exact, Android may show a small alarm icon while the reminder is scheduled.