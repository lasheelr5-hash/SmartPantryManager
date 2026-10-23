# Smart Pantry Manager
Java Android application using SQLiteOpenHelper. The app stores pantry items locally and seeds 20 recipes on first database creation. Recipes appear only when all required ingredients and quantities are available. Units g/kg and ml/l are converted; common plurals are normalized.

## Run
Open this folder in Android Studio. Install Android SDK 35 and JDK 17 if prompted, wait for Gradle sync, select an emulator or Android device, then press Run.

## Limitations
Expiry dates are stored but do not trigger system notifications. Recipes use a compact ingredient string in the recipes table rather than a normalized join table. Review and test on your own device before submission.
