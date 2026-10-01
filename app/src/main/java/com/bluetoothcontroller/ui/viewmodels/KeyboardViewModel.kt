package com.bluetoothcontroller.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bluetoothcontroller.bluetooth.ConnectionState
import com.bluetoothcontroller.keyboard.KeyboardState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class KeyboardViewModel(application: Application) : AndroidViewModel(application) {

    private val _keyboardState = MutableStateFlow(KeyboardState())
    val keyboardState: StateFlow<KeyboardState> = _keyboardState.asStateFlow()

    private val _isGamingMode = MutableStateFlow(false)
    val isGamingMode: StateFlow<Boolean> = _isGamingMode.asStateFlow()

    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    fun onKeyDown(keyCode: Int) { }
    
    fun onKeyUp(keyCode: Int) { }

    fun onModifierChange(modifier: Int, pressed: Boolean) { }

    fun toggleGamingMode() {
        _isGamingMode.value = !_isGamingMode.value
    }
}
