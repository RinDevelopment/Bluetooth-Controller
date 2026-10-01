package com.bluetoothcontroller.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bluetoothcontroller.bluetooth.ConnectionState
import com.bluetoothcontroller.bluetooth.HidSupport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _connectedDeviceName = MutableStateFlow<String?>(null)
    val connectedDeviceName: StateFlow<String?> = _connectedDeviceName.asStateFlow()

    private val _hidSupport = MutableStateFlow(HidSupport.UNSUPPORTED)
    val hidSupport: StateFlow<HidSupport> = _hidSupport.asStateFlow()

    // Internal initialization logic here connecting to BluetoothManager
}
