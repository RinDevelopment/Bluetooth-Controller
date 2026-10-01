package com.bluetoothcontroller.bluetooth

import com.bluetoothcontroller.controller.ControllerState
import com.bluetoothcontroller.controller.DpadDirection
import com.bluetoothcontroller.controller.GameButton
import com.bluetoothcontroller.keyboard.KeyboardState
import com.bluetoothcontroller.mouse.MouseState

object HidReportBuilder {

    fun buildKeyboardReport(state: KeyboardState): ByteArray {
        val report = ByteArray(8)
        report[0] = state.modifiers.toByte()
        report[1] = 0 // Reserved
        val keys = state.pressedKeys.take(6).toList()
        for (i in keys.indices) {
            report[2 + i] = keys[i].toByte()
        }
        return report
    }

    fun buildMouseReport(state: MouseState): ByteArray {
        val report = ByteArray(7)
        report[0] = state.buttons.toByte()
        
        // X movement (signed 16-bit)
        report[1] = (state.deltaX and 0xFF).toByte()
        report[2] = ((state.deltaX shr 8) and 0xFF).toByte()
        
        // Y movement (signed 16-bit)
        report[3] = (state.deltaY and 0xFF).toByte()
        report[4] = ((state.deltaY shr 8) and 0xFF).toByte()
        
        // Wheel (signed 8-bit)
        report[5] = state.wheelDelta.toByte()
        
        // Padding
        report[6] = 0
        
        return report
    }

    fun buildGamepadReport(state: ControllerState): ByteArray {
        val report = ByteArray(13) // 16 bits buttons (2) + 8 bits hat (1) + 4 * 16 bits sticks (8) + 2 * 8 bits triggers (2) = 13 bytes
        
        // Buttons
        var buttons = 0
        if (state.buttons.contains(GameButton.A)) buttons = buttons or (1 shl 0)
        if (state.buttons.contains(GameButton.B)) buttons = buttons or (1 shl 1)
        if (state.buttons.contains(GameButton.X)) buttons = buttons or (1 shl 2)
        if (state.buttons.contains(GameButton.Y)) buttons = buttons or (1 shl 3)
        if (state.buttons.contains(GameButton.L1)) buttons = buttons or (1 shl 4)
        if (state.buttons.contains(GameButton.R1)) buttons = buttons or (1 shl 5)
        if (state.buttons.contains(GameButton.L2_DIGITAL)) buttons = buttons or (1 shl 6)
        if (state.buttons.contains(GameButton.R2_DIGITAL)) buttons = buttons or (1 shl 7)
        if (state.buttons.contains(GameButton.SELECT)) buttons = buttons or (1 shl 8)
        if (state.buttons.contains(GameButton.START)) buttons = buttons or (1 shl 9)
        if (state.buttons.contains(GameButton.LEFT_STICK_PRESS)) buttons = buttons or (1 shl 10)
        if (state.buttons.contains(GameButton.RIGHT_STICK_PRESS)) buttons = buttons or (1 shl 11)
        
        report[0] = (buttons and 0xFF).toByte()
        report[1] = ((buttons shr 8) and 0xFF).toByte()
        
        // Hat switch
        val hat = when (state.dpadDirection) {
            DpadDirection.UP -> 1
            DpadDirection.UP_RIGHT -> 2
            DpadDirection.RIGHT -> 3
            DpadDirection.DOWN_RIGHT -> 4
            DpadDirection.DOWN -> 5
            DpadDirection.DOWN_LEFT -> 6
            DpadDirection.LEFT -> 7
            DpadDirection.UP_LEFT -> 8
            DpadDirection.NONE -> 0
        }
        report[2] = hat.toByte()
        
        // Left stick X
        val lx = (state.leftStickX * 32767).toInt().coerceIn(-32768, 32767)
        report[3] = (lx and 0xFF).toByte()
        report[4] = ((lx shr 8) and 0xFF).toByte()
        
        // Left stick Y
        val ly = (state.leftStickY * 32767).toInt().coerceIn(-32768, 32767)
        report[5] = (ly and 0xFF).toByte()
        report[6] = ((ly shr 8) and 0xFF).toByte()
        
        // Right stick X
        val rx = (state.rightStickX * 32767).toInt().coerceIn(-32768, 32767)
        report[7] = (rx and 0xFF).toByte()
        report[8] = ((rx shr 8) and 0xFF).toByte()
        
        // Right stick Y
        val ry = (state.rightStickY * 32767).toInt().coerceIn(-32768, 32767)
        report[9] = (ry and 0xFF).toByte()
        report[10] = ((ry shr 8) and 0xFF).toByte()
        
        // Left trigger
        val lt = (state.leftTrigger * 255).toInt().coerceIn(0, 255)
        report[11] = lt.toByte()
        
        // Right trigger
        val rt = (state.rightTrigger * 255).toInt().coerceIn(0, 255)
        report[12] = rt.toByte()
        
        return report
    }
}
