package com.bluetoothcontroller.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

class FallbackManager {
    private val TAG = "FallbackManager"
    private val APP_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66")

    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _isServer = MutableStateFlow(false)
    val isServer: StateFlow<Boolean> = _isServer.asStateFlow()

    private val _connectedDevice = MutableStateFlow<BluetoothDeviceInfo?>(null)
    val connectedDevice: StateFlow<BluetoothDeviceInfo?> = _connectedDevice.asStateFlow()

    private var serverSocket: BluetoothServerSocket? = null
    private var socket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null
    private var inputStream: InputStream? = null

    private var listener: ((Byte, ByteArray) -> Unit)? = null

    private val scope = CoroutineScope(Dispatchers.IO)
    private var connectionJob: Job? = null
    private var listenJob: Job? = null

    @SuppressLint("MissingPermission")
    fun startServer(context: Context) {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val adapter = bluetoothManager.adapter ?: return

        _isServer.value = true
        _connectionState.value = ConnectionState.CONNECTING

        connectionJob = scope.launch {
            try {
                serverSocket = adapter.listenUsingRfcommWithServiceRecord("BluetoothControllerFallback", APP_UUID)
                socket = serverSocket?.accept()
                serverSocket?.close() // Accept only one connection

                if (socket != null) {
                    setupConnection(socket!!)
                }
            } catch (e: SecurityException) {
                Log.e(TAG, "SecurityException in startServer", e)
                _connectionState.value = ConnectionState.ERROR
            } catch (e: IOException) {
                Log.e(TAG, "IOException in startServer", e)
                _connectionState.value = ConnectionState.ERROR
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun connectToServer(context: Context, address: String) {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val adapter = bluetoothManager.adapter ?: return

        _isServer.value = false
        _connectionState.value = ConnectionState.CONNECTING

        connectionJob = scope.launch {
            try {
                val device = adapter.getRemoteDevice(address)
                socket = device.createRfcommSocketToServiceRecord(APP_UUID)
                adapter.cancelDiscovery()
                socket?.connect()

                if (socket != null) {
                    setupConnection(socket!!)
                }
            } catch (e: SecurityException) {
                Log.e(TAG, "SecurityException in connectToServer", e)
                _connectionState.value = ConnectionState.ERROR
            } catch (e: IOException) {
                Log.e(TAG, "IOException in connectToServer", e)
                _connectionState.value = ConnectionState.ERROR
            }
        }
    }

    private suspend fun setupConnection(btSocket: BluetoothSocket) {
        socket = btSocket
        try {
            outputStream = btSocket.outputStream
            inputStream = btSocket.inputStream
            
            _connectionState.value = ConnectionState.CONNECTED
            
            // Start listening for incoming data
            listenJob = scope.launch {
                listenForData()
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error setting up streams", e)
            _connectionState.value = ConnectionState.ERROR
        }
    }

    private fun listenForData() {
        val buffer = ByteArray(1024)
        var bytes: Int

        while (_connectionState.value == ConnectionState.CONNECTED) {
            try {
                bytes = inputStream?.read(buffer) ?: 0
                if (bytes > 0) {
                    val type = buffer[0]
                    val data = buffer.copyOfRange(1, bytes)
                    listener?.invoke(type, data)
                }
            } catch (e: IOException) {
                Log.e(TAG, "Input stream disconnected", e)
                _connectionState.value = ConnectionState.DISCONNECTED
                break
            }
        }
    }

    fun sendData(type: Byte, data: ByteArray) {
        scope.launch {
            try {
                val payload = ByteArray(data.size + 1)
                payload[0] = type
                System.arraycopy(data, 0, payload, 1, data.size)
                outputStream?.write(payload)
                outputStream?.flush()
            } catch (e: IOException) {
                Log.e(TAG, "Error sending data", e)
                _connectionState.value = ConnectionState.DISCONNECTED
            }
        }
    }

    fun setDataListener(listener: (Byte, ByteArray) -> Unit) {
        this.listener = listener
    }

    fun disconnect() {
        close()
    }

    fun close() {
        try {
            serverSocket?.close()
            socket?.close()
            inputStream?.close()
            outputStream?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error closing connection", e)
        } finally {
            _connectionState.value = ConnectionState.DISCONNECTED
            _connectedDevice.value = null
            connectionJob?.cancel()
            listenJob?.cancel()
        }
    }
}
