package com.bluetoothcontroller.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bluetoothcontroller.bluetooth.ConnectionState
import com.bluetoothcontroller.controller.ControllerState
import com.bluetoothcontroller.controller.GameButton
import com.bluetoothcontroller.controller.DpadDirection
import com.bluetoothcontroller.controller.ControllerProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ControllerViewModel(application: Application) : AndroidViewModel(application) {

    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _controllerState = MutableStateFlow(ControllerState())
    val controllerState: StateFlow<ControllerState> = _controllerState.asStateFlow()

    private val _currentProfile = MutableStateFlow<ControllerProfile?>(null)
    val currentProfile: StateFlow<ControllerProfile?> = _currentProfile.asStateFlow()

    private val _profiles = MutableStateFlow<List<ControllerProfile>>(emptyList())
    val profiles: StateFlow<List<ControllerProfile>> = _profiles.asStateFlow()

    private val _latencyMs = MutableStateFlow(0L)
    val latencyMs: StateFlow<Long> = _latencyMs.asStateFlow()

    fun onButtonPress(button: GameButton) { }
    
    fun onButtonRelease(button: GameButton) { }

    fun onJoystickMove(isLeft: Boolean, x: Float, y: Float) { }

    fun onDpadPress(direction: DpadDirection) { }

    fun onTriggerChange(isLeft: Boolean, value: Float) { }

    fun selectProfile(profileId: String) {
        // Logic to select profile by ID
    }
}
