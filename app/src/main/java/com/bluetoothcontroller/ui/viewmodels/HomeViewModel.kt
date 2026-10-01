package com.bluetoothcontroller.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bluetoothcontroller.BluetoothControllerApp
import com.bluetoothcontroller.bluetooth.ConnectionState
import com.bluetoothcontroller.bluetooth.HidSupport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as BluetoothControllerApp
    private val bluetoothManager = app.bluetoothManager

    val connectionState: StateFlow<ConnectionState> = bluetoothManager.connectionState

    private val _connectedDeviceName = MutableStateFlow<String?>(null)
    val connectedDeviceName: StateFlow<String?> = _connectedDeviceName.asStateFlow()

    val hidSupport: StateFlow<HidSupport> = bluetoothManager.hidSupport

    init {
        viewModelScope.launch {
            bluetoothManager.connectedDevice.collect { device ->
                _connectedDeviceName.value = device?.name ?: device?.address
            }
        }
    }
}
