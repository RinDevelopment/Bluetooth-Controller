package com.bluetoothcontroller

import android.app.Application
import android.os.Build
import com.bluetoothcontroller.bluetooth.BluetoothManager
import com.bluetoothcontroller.bluetooth.HidDeviceManager

class BluetoothControllerApp : Application() {

    val hidDeviceManager: HidDeviceManager by lazy { 
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            HidDeviceManager()
        } else {
            // For older APIs, it might crash, but HidDeviceManager uses P anyway
            HidDeviceManager() 
        }
    }
    
    val bluetoothManager: BluetoothManager by lazy { 
        BluetoothManager(hidDeviceManager) 
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize application level components here
    }
}
