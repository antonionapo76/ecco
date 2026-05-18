# You Are The Best - Daily Affirmation App

An Android app that reminds you every single day that **you are the best**.

## Features

- **Beautiful Affirmation Cards** - Tap to get a new motivational message with smooth animations
- **Daily Notifications** - Receive an uplifting reminder every morning at 9:00 AM
- **40+ Unique Messages** - A variety of affirmations to brighten your day
- **Survives Reboots** - Reminders persist even after your phone restarts
- **Modern UI** - Built with Jetpack Compose and Material 3 design
- **Dark Mode Support** - Looks great in both light and dark themes

## Screenshots

The app features:
- A pulsing star emoji header
- An animated affirmation card with gradient background
- A "New Affirmation" button to cycle through messages
- A toggle to enable/disable daily reminders

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose with Material 3
- **Notifications**: AlarmManager for exact daily scheduling
- **Minimum SDK**: Android 8.0 (API 26)
- **Target SDK**: Android 14 (API 34)

## Building

1. Open the project in Android Studio (Hedgehog or later)
2. Sync Gradle files
3. Run on a device or emulator (API 26+)

```bash
./gradlew assembleDebug
```

## How It Works

1. On first launch, the app automatically schedules a daily reminder for 9:00 AM
2. Each notification includes a random affirmation title and message
3. Tapping the notification opens the app with a fresh affirmation
4. The boot receiver re-schedules the alarm if the device restarts
5. You can toggle reminders on/off from the main screen

## Project Structure

```
app/src/main/java/com/youarethebest/app/
├── MainActivity.kt          # Main UI with Jetpack Compose
├── Affirmations.kt          # 40+ motivational messages
├── notification/
│   ├── ReminderReceiver.kt  # Handles alarm broadcasts & shows notifications
│   ├── ReminderScheduler.kt # Schedules/cancels daily alarms
│   └── BootReceiver.kt      # Re-schedules after device reboot
└── ui/theme/
    └── Theme.kt             # Material 3 color scheme
```

## Permissions

- `POST_NOTIFICATIONS` - To show daily reminder notifications
- `RECEIVE_BOOT_COMPLETED` - To reschedule reminders after reboot
- `SCHEDULE_EXACT_ALARM` / `USE_EXACT_ALARM` - For precise daily scheduling
