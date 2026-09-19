# Xtreme Player — repair pass

## What changed
- Live TV categories now open the real in-app Live TV screen instead of the old grid/external-player screen.
- Live TV uses Media3 ExoPlayer inside the app.
- One selected `streamId` drives the highlighted list row, playing stream, channel name and channel logo.
- D-pad UP/DOWN while the player has focus changes channel and updates the left list.
- Previous/Next buttons are available on the player overlay.
- Channel logos use Coil, avoiding recycled RecyclerView image races.
- Long-pressing a live channel toggles its favourite state.
- Room database version is bumped to 2 so the old development schema is replaced instead of producing the identity-hash crash.
- Category sync replaces only the selected category and preserves favourite state.
- Splash screen now uses the Xtreme Player logo with an entrance animation and no blank screen.
- Dashboard now displays the same logo.
- Media3 HLS support was added.

## Important
The build could not be run in this environment because the Gradle wrapper tried to download Gradle 8.4 and this environment has no internet access. Run the build in your Codespace.

## Build
```bash
./gradlew clean assembleDebug
```

If the old app is installed and Room still reports an identity-hash problem, uninstall the old app once (or clear its app data), then install the new APK.
