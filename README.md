# The Best Reminder

A simple Android app that reminds you every day that you are the best.

## What it does

- Shows a clean affirmation-focused home screen
- Lets you choose a reminder time
- Sends a daily Android notification saying you are the best
- Re-schedules the reminder after device reboot or app update

## Tech stack

- Kotlin
- Jetpack Compose
- AlarmManager + BroadcastReceiver notifications
- Gradle wrapper included

## Run it

1. Open the project in Android Studio.
2. Make sure your Android SDK is installed and configured.
3. Sync Gradle.
4. Run the `app` configuration on an Android device or emulator.

## Test

Run local unit tests with:

```bash
./gradlew testDebugUnitTest
```