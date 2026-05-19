# Geometry for Francesco

Geometry for Francesco is a native Android quiz app for practicing geometry area questions.

## Features

- Start and Finish buttons for the test flow
- 2D and 3D checkboxes to choose question types
- Three answer buttons for every question, with only one correct answer
- Feedback: "Congrats Francesco!" for correct answers and "Try again." for incorrect answers
- A drawn geometry figure for every question
- 2D area questions: triangle, square, rectangle, circle, trapezoid, parallelogram
- 3D surface area questions: sphere, cylinder, cube, rectangular prism, cone

## Build

Open this folder in Android Studio, or build from the command line once the Android SDK is installed.

Use JDK 17 to build the project. On Windows, the easiest option is Android Studio's bundled JDK:

```powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
```

Then run:

```bash
./gradlew assembleDebug
```

The app is written in Java and uses platform Android APIs only.
