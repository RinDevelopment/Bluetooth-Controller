package com.bluetoothcontroller

import android.app.Application
import android.os.Build
import com.bluetoothcontroller.bluetooth.BluetoothManager
import com.bluetoothcontroller.bluetooth.FallbackManager
import com.bluetoothcontroller.bluetooth.HidDeviceManager
import com.bluetoothcontroller.bluetooth.BluetoothPermissions
import com.bluetoothcontroller.controller.InputEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BluetoothControllerApp : Application() {

    val hidDeviceManager: HidDeviceManager by lazy { 
        HidDeviceManager() 
    }

    val fallbackManager: FallbackManager by lazy {
        FallbackManager()
    }
    
    val bluetoothManager: BluetoothManager by lazy { 
        BluetoothManager(hidDeviceManager, fallbackManager) 
    }

    val inputEngine: InputEngine by lazy {
        InputEngine(
            scope = CoroutineScope(Dispatchers.Default),
            hidDeviceManager = hidDeviceManager,
            fallbackManager = fallbackManager
        )
    }

    override fun onCreate() {
        super.onCreate()
        if (BluetoothPermissions.hasRequiredPermissions(this)) {
            bluetoothManager.initialize(this)
        }
    }
}
