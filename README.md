# BT Controller

> Turn your Android phone into a wireless Bluetooth game controller, keyboard, and mouse.

## Features
- 🎮 Full game controller with analog sticks, D-pad, ABXY, triggers, bumpers
- ⌨️ Complete keyboard with QWERTY and gaming layouts
- 🖱️ Mouse/touchpad with gestures
- 📱 Works between two Android phones via Bluetooth
- 🔒 No internet, no cloud, no accounts required
- 🎨 Customizable layouts and profiles

## How It Works
Phone A -> Bluetooth -> Phone B

Using the standard Bluetooth HID (Human Interface Device) profiles, Phone A acts as a standard input device. This allows it to connect directly to any supported device, operating just like a real controller, mouse, or keyboard. When HID Device mode is not supported by the OS/hardware, a Fallback mode is available, which requires the app to be installed on both phones.

## Requirements
- Android 9.0 (API 28) or higher
- Bluetooth support
- For HID Device mode: Device must support BluetoothHidDevice profile (most modern Android phones do)

## Supported Android Versions
- Android 9 (API 28): BluetoothHidDevice API introduced
- Android 12 (API 31): New Bluetooth permission model
- Android 13+: Foreground service type requirements
- Android 14+: FOREGROUND_SERVICE_CONNECTED_DEVICE type

## Building
1. Open in Android Studio (Hedgehog or newer)
2. Sync Gradle
3. Build -> Make Project
4. Run on device or generate APK

## Installation
Install the APK on both phones.

## Pairing Two Phones
1. Install app on both phones
2. On Phone A: Select "Controller" role
3. On Phone B: Select "Receiver" role  
4. On Phone A: Go to Connect Device
5. Ensure Bluetooth is enabled on both
6. Pair the devices through the app or system settings
7. On Phone A: Select Phone B from paired devices and tap Connect
8. Wait for connection confirmation
9. Select Controller, Keyboard, or Mouse mode

## HID Device Mode
In this mode, Phone A presents itself to Phone B as a real physical input device using Bluetooth HID protocols. No extra software is required on the receiver if it supports Bluetooth keyboards/controllers.

## HID Limitations
- Not all Android devices support acting as a Bluetooth HID Device
- Some devices may have manufacturer-specific limitations
- The target device (Phone B) must support Bluetooth HID Host profile
- Gaming apps must independently support gamepad input
- HID reports are limited to standard USB HID specifications

## Fallback Mode
When HID is unavailable, the Fallback Mode transmits input data to the receiver phone which processes it using an accessibility service or similar injection mechanism. Both phones need the app installed. **This is NOT equivalent to system HID** and might not be compatible with all games or apps.

## Controller Profiles
The app includes several built-in profiles for different types of games. You can create custom layouts by modifying button placements and configuring dead zones, response curves, and input sensitivities.

## Troubleshooting
- **Bluetooth won't turn on**: Ensure permissions are granted
- **Can't find device**: Ensure devices are discoverable
- **Pairing fails**: Clear Bluetooth cache and restart devices
- **Connection drops**: Keep devices close to avoid interference
- **HID not supported**: Try Fallback mode
- **Input not recognized**: Check receiver app compatibility
- **High latency**: Disconnect from other Bluetooth devices (headphones, smartwatches)
- **App crashes**: Check Logcat and submit an issue

## Known Device-Specific Limitations
- **Samsung**: Some models may restrict HID Device profile
- **Pixel**: Generally good HID support
- **Xiaomi**: May require additional Bluetooth permissions in system settings
- **Huawei**: HID Device support varies by model

## Architecture
- `controller`: Input state representations and mapping logic
- `keyboard`: Keycode handling and state
- `mouse`: Delta calculation and buttons
- `bluetooth`: HID Descriptors, report builders, and connection management
- `service`: Foreground services handling background connection longevity

## Privacy
- All input stays on your devices
- No internet connection used
- No data collection
- No analytics
- No advertising
- Keyboard input is never logged

## License
MIT License

## Testing
To run the comprehensive unit test suite:
`./gradlew test`
