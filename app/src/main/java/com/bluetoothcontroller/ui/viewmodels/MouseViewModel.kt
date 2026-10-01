package com.bluetoothcontroller.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bluetoothcontroller.BluetoothControllerApp
import com.bluetoothcontroller.bluetooth.ConnectionState
import com.bluetoothcontroller.controller.InputMode
import com.bluetoothcontroller.mouse.MouseMapper
import com.bluetoothcontroller.mouse.MouseState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MouseViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as BluetoothControllerApp
    private val inputEngine = app.inputEngine
    private val bluetoothManager = app.bluetoothManager

    val mouseState: StateFlow<MouseState> = inputEngine.mouseState
    val connectionState: StateFlow<ConnectionState> = bluetoothManager.connectionState

    init {
        inputEngine.setMode(InputMode.MOUSE)
        inputEngine.startSending()
    }

    fun onMove(dx: Float, dy: Float, sensitivity: Float = 1.0f) {
        val (finalDx, finalDy) = MouseMapper.processMouseMovement(
            rawDx = dx,
            rawDy = dy,
            sensitivity = sensitivity,
            acceleration = true,
            invertY = false
        )
        inputEngine.updateMouseState(inputEngine.mouseState.value.withMovement(finalDx, finalDy))
    }
    
    fun onButtonPress(button: Int) {
        inputEngine.updateMouseState(inputEngine.mouseState.value.withButton(button, true))
    }
    
    fun onButtonRelease(button: Int) {
        inputEngine.updateMouseState(inputEngine.mouseState.value.withButton(button, false))
    }

    fun clickButton(button: Int) {
        viewModelScope.launch {
            onButtonPress(button)
            delay(40)
            onButtonRelease(button)
        }
    }
    
    fun onScroll(delta: Float) {
        val scrollVal = MouseMapper.processScrollInput(
            rawDelta = delta,
            scrollSpeed = 1.0f,
            invertScroll = false
        )
        inputEngine.updateMouseState(inputEngine.mouseState.value.withWheel(scrollVal))
    }
}
