# Test Preparation Android App

Build commands:

- Debug APK: `./gradlew assembleDebug` -> `app/build/outputs/apk/debug/app-debug.apk`
- Release APK: `./gradlew assembleRelease` -> `app/build/outputs/apk/release/app-release.apk`

If no `questions.txt` is imported yet, the app auto-imports `app/src/main/assets/questions.txt` on first run.
