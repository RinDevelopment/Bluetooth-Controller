package com.bluetoothcontroller.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bluetoothcontroller.BluetoothControllerApp
import com.bluetoothcontroller.bluetooth.BluetoothDeviceInfo
import com.bluetoothcontroller.bluetooth.ConnectionState
import com.bluetoothcontroller.bluetooth.HidSupport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PairingViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as BluetoothControllerApp
    private val bluetoothManager = app.bluetoothManager

    val isBluetoothEnabled: StateFlow<Boolean> = bluetoothManager.isBluetoothEnabled
    val pairedDevices: StateFlow<List<BluetoothDeviceInfo>> = bluetoothManager.pairedDevices
    val discoveredDevices: StateFlow<List<BluetoothDeviceInfo>> = bluetoothManager.discoveredDevices
    val connectionState: StateFlow<ConnectionState> = bluetoothManager.connectionState
    val hidSupport: StateFlow<HidSupport> = bluetoothManager.hidSupport
    val errorMessage: StateFlow<String?> = bluetoothManager.errorMessage

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    init {
        refreshDevices()
    }

    fun refreshDevices() {
        bluetoothManager.initialize(getApplication())
        bluetoothManager.getPairedDevices(getApplication())
    }

    fun startScan() {
        _isScanning.value = true
        bluetoothManager.startDiscovery(getApplication())
    }

    fun stopScan() {
        _isScanning.value = false
        bluetoothManager.stopDiscovery(getApplication())
    }

    fun connectToDevice(address: String) {
        bluetoothManager.connectToDevice(getApplication(), address)
    }

    fun disconnect() {
        bluetoothManager.disconnect()
    }

    fun enableBluetooth() {
        bluetoothManager.initialize(getApplication())
    }
}
