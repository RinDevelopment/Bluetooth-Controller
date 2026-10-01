package com.bluetoothcontroller.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothHidDevice
import android.bluetooth.BluetoothHidDeviceAppSdpSettings
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.bluetoothcontroller.controller.ControllerState
import com.bluetoothcontroller.keyboard.KeyboardState
import com.bluetoothcontroller.mouse.MouseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.Executors

@RequiresApi(Build.VERSION_CODES.P)
class HidDeviceManager {
    private val TAG = "HidDeviceManager"

    private val _isRegistered = MutableStateFlow(false)
    val isRegistered: StateFlow<Boolean> = _isRegistered.asStateFlow()

    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _connectedDevice = MutableStateFlow<BluetoothDeviceInfo?>(null)
    val connectedDevice: StateFlow<BluetoothDeviceInfo?> = _connectedDevice.asStateFlow()

    private var hidDevice: BluetoothHidDevice? = null
    private var actualDevice: BluetoothDevice? = null

    private val sdpSettings = BluetoothHidDeviceAppSdpSettings(
        "Bluetooth Controller",
        "A Bluetooth Controller app",
        "Bluetooth Controller",
        BluetoothHidDevice.SUBCLASS1_COMBO,
        HidDescriptors.COMBINED_DESCRIPTOR
    )

    private val callback = object : BluetoothHidDevice.Callback() {
        override fun onAppStatusChanged(pluggedDevice: BluetoothDevice?, registered: Boolean) {
            super.onAppStatusChanged(pluggedDevice, registered)
            _isRegistered.value = registered
        }

        override fun onConnectionStateChanged(device: BluetoothDevice?, state: Int) {
            super.onConnectionStateChanged(device, state)
            when (state) {
                BluetoothProfile.STATE_CONNECTED -> {
                    _connectionState.value = ConnectionState.CONNECTED
                    if (device != null) {
                        try {
                            @SuppressLint("MissingPermission")
                            val info = BluetoothDeviceInfo(
                                name = device.name,
                                address = device.address,
                                isPaired = device.bondState == BluetoothDevice.BOND_BONDED,
                                isConnected = true
                            )
                            _connectedDevice.value = info
                            actualDevice = device
                        } catch (e: SecurityException) {
                            Log.e(TAG, "SecurityException getting device info")
                        }
                    }
                }
                BluetoothProfile.STATE_CONNECTING -> {
                    _connectionState.value = ConnectionState.CONNECTING
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    _connectionState.value = ConnectionState.DISCONNECTED
                    _connectedDevice.value = null
                    actualDevice = null
                }
                BluetoothProfile.STATE_DISCONNECTING -> {
                    // Do nothing
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun registerHidDevice(context: Context): Boolean {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter ?: return false

        return bluetoothAdapter.getProfileProxy(
            context,
            object : BluetoothProfile.ServiceListener {
                override fun onServiceConnected(profile: Int, proxy: BluetoothProfile?) {
                    if (profile == BluetoothProfile.HID_DEVICE) {
                        hidDevice = proxy as? BluetoothHidDevice
                        try {
                            hidDevice?.registerApp(
                                sdpSettings,
                                null,
                                null,
                                Executors.newSingleThreadExecutor(),
                                callback
                            )
                        } catch (e: SecurityException) {
                            Log.e(TAG, "SecurityException registering HID device")
                        }
                    }
                }

                override fun onServiceDisconnected(profile: Int) {
                    if (profile == BluetoothProfile.HID_DEVICE) {
                        hidDevice = null
                        _isRegistered.value = false
                        _connectionState.value = ConnectionState.DISCONNECTED
                    }
                }
            },
            BluetoothProfile.HID_DEVICE
        )
    }

    @SuppressLint("MissingPermission")
    fun unregisterHidDevice() {
        try {
            hidDevice?.unregisterApp()
        } catch (e: SecurityException) {
            Log.e(TAG, "SecurityException unregistering HID device")
        }
    }

    @SuppressLint("MissingPermission")
    fun sendReport(reportId: Int, report: ByteArray): Boolean {
        if (actualDevice == null || hidDevice == null) return false
        return try {
            hidDevice?.sendReport(actualDevice, reportId, report) ?: false
        } catch (e: SecurityException) {
            Log.e(TAG, "SecurityException sending report")
            false
        } catch (e: Exception) {
            Log.e(TAG, "Exception sending report", e)
            false
        }
    }

    fun sendKeyboardReport(state: KeyboardState): Boolean {
        return sendReport(1, HidReportBuilder.buildKeyboardReport(state))
    }

    fun sendMouseReport(state: MouseState): Boolean {
        return sendReport(2, HidReportBuilder.buildMouseReport(state))
    }

    fun sendGamepadReport(state: ControllerState): Boolean {
        return sendReport(3, HidReportBuilder.buildGamepadReport(state))
    }

    @SuppressLint("MissingPermission")
    fun connect(device: BluetoothDevice): Boolean {
        actualDevice = device
        return try {
            hidDevice?.connect(device) ?: false
        } catch (e: SecurityException) {
            Log.e(TAG, "SecurityException connecting HID device", e)
            false
        }
    }

    @SuppressLint("MissingPermission")
    fun disconnect() {
        try {
            if (actualDevice != null && hidDevice != null) {
                hidDevice?.disconnect(actualDevice)
            }
            _connectionState.value = ConnectionState.DISCONNECTED
            _connectedDevice.value = null
            actualDevice = null
        } catch (e: SecurityException) {
            Log.e(TAG, "SecurityException in disconnect")
        }
    }
}
