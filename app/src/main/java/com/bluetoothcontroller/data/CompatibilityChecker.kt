package com.bluetoothcontroller.data

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

enum class CompatLevel {
    SUPPORTED, LIMITED, UNSUPPORTED
}

data class CompatibilityResult(
    val androidVersion: CompatLevel,
    val bluetooth: CompatLevel,
    val bluetoothLE: CompatLevel,
    val bluetoothClassic: CompatLevel,
    val hidDevice: CompatLevel,
    val permissions: CompatLevel,
    val overall: CompatLevel,
    val details: List<String>
)

object CompatibilityChecker {
    
    fun checkCompatibility(context: Context): CompatibilityResult {
        val details = mutableListOf<String>()
        
        // Android Version Check (Needs Android 9 / API 28+ for BluetoothHidDevice)
        val androidVersion = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            CompatLevel.SUPPORTED
        } else {
            details.add("Android 9 (API 28) or higher is required for Bluetooth HID profiles.")
            CompatLevel.UNSUPPORTED
        }

        val packageManager = context.packageManager
        
        // Bluetooth Hardware Check
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
        val bluetoothAdapter = bluetoothManager?.adapter
        val bluetooth = if (bluetoothAdapter != null) {
            CompatLevel.SUPPORTED
        } else {
            details.add("Device does not have a Bluetooth adapter.")
            CompatLevel.UNSUPPORTED
        }

        // Bluetooth LE Check
        val bluetoothLE = if (packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            CompatLevel.SUPPORTED
        } else {
            details.add("Device does not support Bluetooth Low Energy (BLE).")
            CompatLevel.LIMITED
        }
        
        // Bluetooth Classic Check
        val bluetoothClassic = if (packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            CompatLevel.SUPPORTED
        } else {
            details.add("Device does not support standard Bluetooth.")
            CompatLevel.UNSUPPORTED
        }

        // HID Device Profile Check (Requires API 28)
        var hidDevice = CompatLevel.UNSUPPORTED
        if (androidVersion == CompatLevel.SUPPORTED && bluetoothAdapter != null) {
            var supported = false
            try {
                val listener = object : BluetoothProfile.ServiceListener {
                    override fun onServiceConnected(profile: Int, proxy: BluetoothProfile?) {
                        supported = proxy != null
                        bluetoothAdapter.closeProfileProxy(profile, proxy)
                    }
                    override fun onServiceDisconnected(profile: Int) {}
                }
                // getProfileProxy returns true if the profile is supported by the device
                supported = bluetoothAdapter.getProfileProxy(context, listener, BluetoothProfile.HID_DEVICE)
            } catch (e: Exception) {
                supported = false
            }
            if (supported) {
                hidDevice = CompatLevel.SUPPORTED
            } else {
                details.add("Bluetooth HID Device profile is not supported on this OS/hardware combination.")
            }
        }

        // Permissions Check
        var permissions = CompatLevel.SUPPORTED
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val hasConnect = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
            if (!hasConnect) {
                permissions = CompatLevel.LIMITED
                details.add("Missing BLUETOOTH_CONNECT permission.")
            }
        }

        // Overall Check
        val overall = when {
            androidVersion == CompatLevel.UNSUPPORTED || bluetooth == CompatLevel.UNSUPPORTED || hidDevice == CompatLevel.UNSUPPORTED -> CompatLevel.UNSUPPORTED
            permissions == CompatLevel.LIMITED || bluetoothLE == CompatLevel.LIMITED -> CompatLevel.LIMITED
            else -> CompatLevel.SUPPORTED
        }

        if (overall == CompatLevel.SUPPORTED) {
            details.add("Your device fully supports acting as a Bluetooth Controller.")
        }

        return CompatibilityResult(
            androidVersion = androidVersion,
            bluetooth = bluetooth,
            bluetoothLE = bluetoothLE,
            bluetoothClassic = bluetoothClassic,
            hidDevice = hidDevice,
            permissions = permissions,
            overall = overall,
            details = details
        )
    }
}
