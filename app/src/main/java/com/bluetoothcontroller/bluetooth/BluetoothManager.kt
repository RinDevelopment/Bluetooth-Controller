package com.bluetoothcontroller.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager as AndroidBluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BluetoothManager(private val hidDeviceManager: HidDeviceManager) {
    private val TAG = "BluetoothManager"

    val connectionState = hidDeviceManager.connectionState
    val connectedDevice = hidDeviceManager.connectedDevice

    private val _pairedDevices = MutableStateFlow<List<BluetoothDeviceInfo>>(emptyList())
    val pairedDevices: StateFlow<List<BluetoothDeviceInfo>> = _pairedDevices.asStateFlow()

    private val _discoveredDevices = MutableStateFlow<List<BluetoothDeviceInfo>>(emptyList())
    val discoveredDevices: StateFlow<List<BluetoothDeviceInfo>> = _discoveredDevices.asStateFlow()

    private val _isBluetoothEnabled = MutableStateFlow(false)
    val isBluetoothEnabled: StateFlow<Boolean> = _isBluetoothEnabled.asStateFlow()

    private val _hidSupport = MutableStateFlow(HidSupport.UNSUPPORTED)
    val hidSupport: StateFlow<HidSupport> = _hidSupport.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private var adapter: BluetoothAdapter? = null
    
    private val scope = CoroutineScope(Dispatchers.Default)

    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                    _isBluetoothEnabled.value = state == BluetoothAdapter.STATE_ON
                }
                BluetoothDevice.ACTION_FOUND -> {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    if (device != null) {
                        try {
                            val info = BluetoothDeviceInfo(
                                name = device.name,
                                address = device.address,
                                isPaired = device.bondState == BluetoothDevice.BOND_BONDED,
                                isConnected = false
                            )
                            _discoveredDevices.value = _discoveredDevices.value + info
                        } catch (e: SecurityException) {
                            Log.e(TAG, "SecurityException getting discovered device info")
                        }
                    }
                }
            }
        }
    }

    fun initialize(context: Context) {
        val manager = context.getSystemService(Context.BLUETOOTH_SERVICE) as AndroidBluetoothManager
        adapter = manager.adapter
        
        _isBluetoothEnabled.value = adapter?.isEnabled == true
        _hidSupport.value = checkHidSupport(context)

        val filter = IntentFilter().apply {
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            addAction(BluetoothDevice.ACTION_FOUND)
        }
        context.registerReceiver(receiver, filter)

        if (_hidSupport.value == HidSupport.FULL) {
            hidDeviceManager.registerHidDevice(context)
        }
    }

    fun checkHidSupport(context: Context): HidSupport {
        if (adapter == null) return HidSupport.UNSUPPORTED
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            HidSupport.FULL
        } else {
            HidSupport.LIMITED
        }
    }

    @SuppressLint("MissingPermission")
    fun startDiscovery(context: Context) {
        try {
            _discoveredDevices.value = emptyList()
            adapter?.startDiscovery()
        } catch (e: SecurityException) {
            _errorMessage.value = "Missing Bluetooth permissions"
        }
    }

    @SuppressLint("MissingPermission")
    fun stopDiscovery(context: Context) {
        try {
            adapter?.cancelDiscovery()
        } catch (e: SecurityException) {
            Log.e(TAG, "Missing permission to stop discovery")
        }
    }

    @SuppressLint("MissingPermission")
    fun getPairedDevices(context: Context): List<BluetoothDeviceInfo> {
        return try {
            adapter?.bondedDevices?.map {
                BluetoothDeviceInfo(
                    name = it.name,
                    address = it.address,
                    isPaired = true,
                    isConnected = false
                )
            } ?: emptyList()
        } catch (e: SecurityException) {
            _errorMessage.value = "Missing permissions to get paired devices"
            emptyList()
        }.also {
            _pairedDevices.value = it
        }
    }

    @SuppressLint("MissingPermission")
    fun connectToDevice(context: Context, address: String) {
        val device = adapter?.getRemoteDevice(address) ?: return
        try {
            // Placeholder: Host must connect to us usually.
        } catch (e: SecurityException) {
            _errorMessage.value = "Missing connect permissions"
        }
    }

    fun disconnect() {
        hidDeviceManager.disconnect()
    }

    fun startReconnection(context: Context) {
        scope.launch {
            var attempt = 1
            while (connectionState.value == ConnectionState.DISCONNECTED && attempt <= 5) {
                // Reconnect logic
                delay(1000L * attempt)
                attempt++
            }
        }
    }

    fun cleanup(context: Context) {
        try {
            context.unregisterReceiver(receiver)
        } catch (e: Exception) {
            Log.e(TAG, "Exception unregistering receiver", e)
        }
        try {
            hidDeviceManager.unregisterHidDevice()
        } catch (e: Exception) {
            Log.e(TAG, "Exception unregistering HID device", e)
        }
    }
}
