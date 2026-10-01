package com.bluetoothcontroller.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bluetoothcontroller.bluetooth.BluetoothDeviceInfo
import com.bluetoothcontroller.bluetooth.ConnectionState
import com.bluetoothcontroller.bluetooth.HidSupport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PairingViewModel(application: Application) : AndroidViewModel(application) {

    private val _isBluetoothEnabled = MutableStateFlow(false)
    val isBluetoothEnabled: StateFlow<Boolean> = _isBluetoothEnabled.asStateFlow()

    private val _pairedDevices = MutableStateFlow<List<BluetoothDeviceInfo>>(emptyList())
    val pairedDevices: StateFlow<List<BluetoothDeviceInfo>> = _pairedDevices.asStateFlow()

    private val _discoveredDevices = MutableStateFlow<List<BluetoothDeviceInfo>>(emptyList())
    val discoveredDevices: StateFlow<List<BluetoothDeviceInfo>> = _discoveredDevices.asStateFlow()

    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _hidSupport = MutableStateFlow(HidSupport.UNSUPPORTED)
    val hidSupport: StateFlow<HidSupport> = _hidSupport.asStateFlow()

    fun startScan() {
        _isScanning.value = true
        // Logic to scan
    }

    fun stopScan() {
        _isScanning.value = false
        // Logic to stop scan
    }

    fun connectToDevice(address: String) {
        _connectionState.value = ConnectionState.CONNECTING
        // Logic to connect
    }

    fun disconnect() {
        // Logic to disconnect
    }

    fun enableBluetooth() {
        // Logic to enable BT
    }
}
