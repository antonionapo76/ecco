# Geometry for Francesco

Geometry for Francesco is a native Android quiz app for practicing geometry questions.

## Features

- Start and Finish buttons for the test flow
- 2D and 3D checkboxes to choose question types
- Randomly generated questions so tests do not repeat the same question during one run
- Three answer buttons for every question, with only one correct answer
- Feedback: "Congrats Francesco!" for correct answers and "Try again." for incorrect answers
- A drawn geometry figure for every question
- 2D area and perimeter questions: multiple triangles, square, rectangle, circle, trapezoid, parallelogram, pentagon, hexagon, octagon
- Formula questions where Francesco chooses the correct formula from three options
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
