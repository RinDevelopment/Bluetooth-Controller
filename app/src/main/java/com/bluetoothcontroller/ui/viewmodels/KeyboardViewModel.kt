package com.bluetoothcontroller.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bluetoothcontroller.BluetoothControllerApp
import com.bluetoothcontroller.bluetooth.ConnectionState
import com.bluetoothcontroller.controller.InputMode
import com.bluetoothcontroller.keyboard.KeyboardMapper
import com.bluetoothcontroller.keyboard.KeyboardState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class KeyboardViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as BluetoothControllerApp
    private val inputEngine = app.inputEngine
    private val bluetoothManager = app.bluetoothManager

    val keyboardState: StateFlow<KeyboardState> = inputEngine.keyboardState
    val connectionState: StateFlow<ConnectionState> = bluetoothManager.connectionState

    private val _isGamingMode = MutableStateFlow(false)
    val isGamingMode: StateFlow<Boolean> = _isGamingMode.asStateFlow()

    init {
        inputEngine.setMode(InputMode.KEYBOARD)
        inputEngine.startSending()
    }

    fun onKeyDown(keyCode: Int) {
        inputEngine.updateKeyboardState(inputEngine.keyboardState.value.withKeyPressed(keyCode))
    }
    
    fun onKeyUp(keyCode: Int) {
        inputEngine.updateKeyboardState(inputEngine.keyboardState.value.withKeyReleased(keyCode))
    }

    fun onModifierChange(modifier: Int, pressed: Boolean) {
        inputEngine.updateKeyboardState(inputEngine.keyboardState.value.withModifier(modifier, pressed))
    }

    fun toggleGamingMode() {
        _isGamingMode.value = !_isGamingMode.value
    }

    fun onKeyLabelPressed(label: String) {
        val upper = label.uppercase()
        when (upper) {
            "CTRL" -> onModifierChange(KeyboardState.MOD_LEFT_CTRL, true)
            "ALT" -> onModifierChange(KeyboardState.MOD_LEFT_ALT, true)
            "SHIFT" -> onModifierChange(KeyboardState.MOD_LEFT_SHIFT, true)
            else -> {
                val code = getKeyCodeForLabel(upper)
                if (code != 0) onKeyDown(code)
            }
        }
    }

    fun onKeyLabelReleased(label: String) {
        val upper = label.uppercase()
        when (upper) {
            "CTRL" -> onModifierChange(KeyboardState.MOD_LEFT_CTRL, false)
            "ALT" -> onModifierChange(KeyboardState.MOD_LEFT_ALT, false)
            "SHIFT" -> onModifierChange(KeyboardState.MOD_LEFT_SHIFT, false)
            else -> {
                val code = getKeyCodeForLabel(upper)
                if (code != 0) onKeyUp(code)
            }
        }
    }

    private fun getKeyCodeForLabel(label: String): Int {
        return when (label) {
            "A" -> KeyboardMapper.KeyCodes.KEY_A
            "B" -> KeyboardMapper.KeyCodes.KEY_B
            "C" -> KeyboardMapper.KeyCodes.KEY_C
            "D" -> KeyboardMapper.KeyCodes.KEY_D
            "E" -> KeyboardMapper.KeyCodes.KEY_E
            "F" -> KeyboardMapper.KeyCodes.KEY_F
            "G" -> KeyboardMapper.KeyCodes.KEY_G
            "H" -> KeyboardMapper.KeyCodes.KEY_H
            "I" -> KeyboardMapper.KeyCodes.KEY_I
            "J" -> KeyboardMapper.KeyCodes.KEY_J
            "K" -> KeyboardMapper.KeyCodes.KEY_K
            "L" -> KeyboardMapper.KeyCodes.KEY_L
            "M" -> KeyboardMapper.KeyCodes.KEY_M
            "N" -> KeyboardMapper.KeyCodes.KEY_N
            "O" -> KeyboardMapper.KeyCodes.KEY_O
            "P" -> KeyboardMapper.KeyCodes.KEY_P
            "Q" -> KeyboardMapper.KeyCodes.KEY_Q
            "R" -> KeyboardMapper.KeyCodes.KEY_R
            "S" -> KeyboardMapper.KeyCodes.KEY_S
            "T" -> KeyboardMapper.KeyCodes.KEY_T
            "U" -> KeyboardMapper.KeyCodes.KEY_U
            "V" -> KeyboardMapper.KeyCodes.KEY_V
            "W" -> KeyboardMapper.KeyCodes.KEY_W
            "X" -> KeyboardMapper.KeyCodes.KEY_X
            "Y" -> KeyboardMapper.KeyCodes.KEY_Y
            "Z" -> KeyboardMapper.KeyCodes.KEY_Z
            "1" -> KeyboardMapper.KeyCodes.KEY_1
            "2" -> KeyboardMapper.KeyCodes.KEY_2
            "3" -> KeyboardMapper.KeyCodes.KEY_3
            "4" -> KeyboardMapper.KeyCodes.KEY_4
            "5" -> KeyboardMapper.KeyCodes.KEY_5
            "6" -> KeyboardMapper.KeyCodes.KEY_6
            "7" -> KeyboardMapper.KeyCodes.KEY_7
            "8" -> KeyboardMapper.KeyCodes.KEY_8
            "9" -> KeyboardMapper.KeyCodes.KEY_9
            "0" -> KeyboardMapper.KeyCodes.KEY_0
            "ENTER" -> KeyboardMapper.KeyCodes.KEY_ENTER
            "ESC" -> KeyboardMapper.KeyCodes.KEY_ESCAPE
            "BACK" -> KeyboardMapper.KeyCodes.KEY_BACKSPACE
            "TAB" -> KeyboardMapper.KeyCodes.KEY_TAB
            "SPACE" -> KeyboardMapper.KeyCodes.KEY_SPACE
            "CAPS" -> KeyboardMapper.KeyCodes.KEY_CAPS_LOCK
            "DEL" -> KeyboardMapper.KeyCodes.KEY_DELETE
            "UP" -> KeyboardMapper.KeyCodes.KEY_UP
            "DOWN" -> KeyboardMapper.KeyCodes.KEY_DOWN
            "LEFT" -> KeyboardMapper.KeyCodes.KEY_LEFT
            "RIGHT" -> KeyboardMapper.KeyCodes.KEY_RIGHT
            "," -> KeyboardMapper.KeyCodes.KEY_COMMA
            "." -> KeyboardMapper.KeyCodes.KEY_PERIOD
            else -> 0
        }
    }
}
