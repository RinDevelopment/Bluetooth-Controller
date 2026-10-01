package com.bluetoothcontroller.controller

import com.bluetoothcontroller.bluetooth.FallbackManager
import com.bluetoothcontroller.bluetooth.HidDeviceManager
import com.bluetoothcontroller.bluetooth.HidReportBuilder
import com.bluetoothcontroller.keyboard.KeyboardState
import com.bluetoothcontroller.mouse.MouseState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Central input processing engine responsible for state holding and HID report delegation.
 */
class InputEngine(
    private val scope: CoroutineScope,
    var hidDeviceManager: HidDeviceManager? = null,
    var fallbackManager: FallbackManager? = null
) {
    private val _controllerState = MutableStateFlow(ControllerState.EMPTY)
    val controllerState: StateFlow<ControllerState> = _controllerState.asStateFlow()

    private val _keyboardState = MutableStateFlow(KeyboardState.EMPTY)
    val keyboardState: StateFlow<KeyboardState> = _keyboardState.asStateFlow()

    private val _mouseState = MutableStateFlow(MouseState.EMPTY)
    val mouseState: StateFlow<MouseState> = _mouseState.asStateFlow()

    private val _currentMode = MutableStateFlow(InputMode.CONTROLLER)
    val currentMode: StateFlow<InputMode> = _currentMode.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private var previousControllerState = ControllerState.EMPTY
    private var previousKeyboardState = KeyboardState.EMPTY
    private var previousMouseState = MouseState.EMPTY

    private var sendingJob: Job? = null

    fun updateControllerState(state: ControllerState) {
        _controllerState.value = state
        sendControllerIfNeeded()
    }

    fun updateKeyboardState(state: KeyboardState) {
        _keyboardState.value = state
        sendKeyboardIfNeeded()
    }

    fun updateMouseState(state: MouseState) {
        _mouseState.value = state
        sendMouseIfNeeded()
    }

    fun setMode(mode: InputMode) {
        _currentMode.value = mode
    }
    
    fun setConnected(connected: Boolean) {
        _isConnected.value = connected
    }

    fun startSending() {
        if (sendingJob?.isActive == true) return
        
        sendingJob = scope.launch(Dispatchers.Default) {
            // Can be used for periodic fallback keep-alives or heartbeat
        }
    }

    fun stopSending() {
        sendingJob?.cancel()
        sendingJob = null
    }

    private fun sendControllerIfNeeded() {
        val current = _controllerState.value
        if (current != previousControllerState && _isConnected.value) {
            scope.launch {
                val sentHid = hidDeviceManager?.sendGamepadReport(current) ?: false
                if (!sentHid) {
                    val reportBytes = HidReportBuilder.buildGamepadReport(current)
                    fallbackManager?.sendData(0x03.toByte(), reportBytes)
                }
            }
            previousControllerState = current
        }
    }

    private fun sendKeyboardIfNeeded() {
        val current = _keyboardState.value
        if (current != previousKeyboardState && _isConnected.value) {
            scope.launch {
                val sentHid = hidDeviceManager?.sendKeyboardReport(current) ?: false
                if (!sentHid) {
                    val reportBytes = HidReportBuilder.buildKeyboardReport(current)
                    fallbackManager?.sendData(0x01.toByte(), reportBytes)
                }
            }
            previousKeyboardState = current
        }
    }

    private fun sendMouseIfNeeded() {
        val current = _mouseState.value
        if (current != previousMouseState && _isConnected.value) {
            scope.launch {
                val sentHid = hidDeviceManager?.sendMouseReport(current) ?: false
                if (!sentHid) {
                    val reportBytes = HidReportBuilder.buildMouseReport(current)
                    fallbackManager?.sendData(0x02.toByte(), reportBytes)
                }
            }
            previousMouseState = current
        }
    }
}
