# Tabelline per Michele

Tabelline per Michele is a small native Android app for practicing multiplication tables from 2 to 12.

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

- Press **Start** to begin.
- The app shows multiplication questions from 2 x 2 through 12 x 12.
- Each question has three possible answer buttons.
- A correct answer praises Michele and immediately shows the next question.
- A wrong answer says to try again and keeps the same question on screen.
- Press **Finish** to stop the practice session.