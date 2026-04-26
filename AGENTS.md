# react-native-paymob

React Native library for Paymob payment SDK (works on iOS and Android).

## Developer Commands

```bash
npm test                 # Run Jest tests (react-native preset)
npm run typescript      # TypeScript typecheck (noEmit)
npm run prepare         # Build with react-native-builder-bob
npm run release         # Release with release-it + conventional-changelog
```

## Architecture

- **Source**: `src/index.tsx` → main entry, exports `Paymob` native module and `useDidDismissPaymob` hook
- **API**: `src/types.ts` → TypeScript definitions
- **Build output**: `lib/` (commonjs, module, typescript)
- **Android native**: `android/src/main/java/com/reactnativepaymob/`
- **Test**: `src/__tests__/index.test.tsx`

## Platform Setup Requirements

### iOS
- Requires Swift bridging header in the consuming app
- Must create an empty `.swift` file in the iOS project

### Android
- In `AndroidManifest.xml`:
  ```xml
  xmlns:tools="http://schemas.android.com/tools"
  android:supportsRtl="false"
  tools:replace="android:supportsRtl, android:allowBackup"
  ```
- Required colors in `android/app/res/values/colors.xml`:
  ```xml
  <color name="white">#FFF</color>
  <color name="colorPrimary">#6200EE</color>
  <color name="colorPrimaryDark">#03DAC5</color>
  <color name="colorAccent">#03DAC5</color>
  <color name="ThemeColor">#FF0000</color>
  ```

## Testing Notes

- Uses `jest` with `react-native` preset
- Test files: `src/__tests__/*.test.tsx`

## Publishing

- Uses `release-it` with Angular conventional-changelog preset
- Publishes to npm registry automatically on release