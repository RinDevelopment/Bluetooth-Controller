package com.bluetoothcontroller.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bluetoothcontroller.bluetooth.ConnectionState
import com.bluetoothcontroller.mouse.MouseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MouseViewModel(application: Application) : AndroidViewModel(application) {

    private val _mouseState = MutableStateFlow(MouseState())
    val mouseState: StateFlow<MouseState> = _mouseState.asStateFlow()

    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    fun onMove(dx: Float, dy: Float) { }
    
    fun onButtonPress(button: Int) { }
    
    fun onButtonRelease(button: Int) { }
    
    fun onScroll(delta: Float) { }
}
