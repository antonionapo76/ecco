# Best Reminder

A tiny Android app that reminds you, every day, that **you are the best**.

It schedules a daily notification at a time you pick, with a rotating set of short affirmations.

## Features

- Pick the time of day you want to be reminded
- Daily repeating notification, scheduled with `WorkManager`
- Reschedules itself automatically after device reboot or app update
- Preview-notification button so you can see what a reminder looks like right now
- Material 3 UI with Jetpack Compose, dynamic color on Android 12+

## Requirements

- Android Studio Hedgehog (2023.1) or newer (anything with AGP 8.5+)
- JDK 17
- Android SDK Platform 34
- A device or emulator running Android 8.0 (API 26) or higher

## Project layout

```
app/src/main/java/com/example/bestreminder/
├── BestReminderApp.kt              # Application + notification channel
├── MainActivity.kt                 # Entry point, hosts Compose UI
├── data/ReminderPrefs.kt           # DataStore-backed reminder time
├── notifications/
│   ├── ReminderNotifier.kt         # Builds and posts the notification
│   ├── ReminderWorker.kt           # WorkManager worker that fires the notification
│   ├── ReminderScheduler.kt        # Schedules / cancels the daily PeriodicWorkRequest
│   └── BootReceiver.kt             # Re-schedules after reboot / app update
└── ui/
    ├── HomeScreen.kt               # The single-screen Compose UI
    └── theme/…                     # Material 3 theme
```

## Build & run

From the project root:

```bash
# Generate the Gradle wrapper jar (one-time, if missing)
gradle wrapper --gradle-version 8.9

# Build a debug APK
./gradlew :app:assembleDebug

# Install to a connected device / emulator
./gradlew :app:installDebug
```

The debug APK ends up at `app/build/outputs/apk/debug/app-debug.apk`.

> The Gradle wrapper JAR (`gradle/wrapper/gradle-wrapper.jar`) is intentionally not
> committed. Run `gradle wrapper` once locally — or open the project in Android
> Studio, which will generate it for you — before invoking `./gradlew`.

## How it works

1. On first launch, the app asks for the `POST_NOTIFICATIONS` permission
   (required on Android 13+).
2. You pick a time on the screen and tap **Save daily reminder**.
3. The choice is persisted with DataStore and a unique periodic
   `WorkManager` job (`daily_best_reminder_work`) is enqueued with an
   initial delay that lands exactly on your chosen time.
4. Each time the worker runs, it posts a notification with a random
   affirmation from `ReminderNotifier.affirmations`.
5. After a reboot or app update, `BootReceiver` reads the saved time and
   reschedules the job.

`PeriodicWorkRequest` has a minimum interval of 15 minutes and a flex
window, so the notification may arrive a few minutes around the target
time — which is plenty accurate for a daily affirmation.

## Customizing affirmations

Edit the list in `app/src/main/java/com/example/bestreminder/notifications/ReminderNotifier.kt`:

```kotlin
private val affirmations = listOf(
    "You are the best. Today is yours.",
    // add your own…
)
```

## License

MIT — do whatever makes you feel like the best.
