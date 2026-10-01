package com.bluetoothcontroller.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bluetoothcontroller.BluetoothControllerApp
import com.bluetoothcontroller.bluetooth.ConnectionState
import com.bluetoothcontroller.controller.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ControllerViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as BluetoothControllerApp
    private val inputEngine = app.inputEngine
    private val bluetoothManager = app.bluetoothManager

    val connectionState: StateFlow<ConnectionState> = bluetoothManager.connectionState
    val controllerState: StateFlow<ControllerState> = inputEngine.controllerState

    private val _profiles = MutableStateFlow<List<ControllerProfile>>(ControllerProfile.DEFAULT_PROFILES)
    val profiles: StateFlow<List<ControllerProfile>> = _profiles.asStateFlow()

    private val _currentProfile = MutableStateFlow<ControllerProfile?>(ControllerProfile.DEFAULT_PROFILES.firstOrNull())
    val currentProfile: StateFlow<ControllerProfile?> = _currentProfile.asStateFlow()

    private val _latencyMs = MutableStateFlow(0L)
    val latencyMs: StateFlow<Long> = _latencyMs.asStateFlow()

    init {
        inputEngine.setMode(InputMode.CONTROLLER)
        inputEngine.startSending()
    }

    fun onButtonPress(button: GameButton) {
        inputEngine.updateControllerState(inputEngine.controllerState.value.withButton(button, true))
    }
    
    fun onButtonRelease(button: GameButton) {
        inputEngine.updateControllerState(inputEngine.controllerState.value.withButton(button, false))
    }

    fun onJoystickMove(isLeft: Boolean, x: Float, y: Float) {
        val current = inputEngine.controllerState.value
        val updated = if (isLeft) {
            current.withLeftStick(x, y)
        } else {
            current.withRightStick(x, y)
        }
        inputEngine.updateControllerState(updated)
    }

    fun onDpadPress(direction: DpadDirection) {
        inputEngine.updateControllerState(inputEngine.controllerState.value.withDpad(direction))
    }

    fun onTriggerChange(isLeft: Boolean, value: Float) {
        val current = inputEngine.controllerState.value
        val updated = if (isLeft) {
            current.withLeftTrigger(value)
        } else {
            current.withRightTrigger(value)
        }
        inputEngine.updateControllerState(updated)
    }

    fun selectProfile(profileId: String) {
        _currentProfile.value = _profiles.value.find { it.id == profileId }
    }
}
