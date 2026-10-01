package com.bluetoothcontroller.bluetooth

enum class ConnectionState {
    DISCONNECTED, CONNECTING, CONNECTED, RECONNECTING, ERROR
}

data class BluetoothDeviceInfo(
    val name: String?,
    val address: String,
    val isPaired: Boolean,
    val isConnected: Boolean
)

enum class HidSupport {
    FULL, LIMITED, UNSUPPORTED
}
