package com.bluetoothcontroller.bluetooth

import com.bluetoothcontroller.controller.ControllerState
import com.bluetoothcontroller.controller.DpadDirection
import com.bluetoothcontroller.controller.GameButton
import com.bluetoothcontroller.keyboard.KeyboardMapper.KeyCodes
import com.bluetoothcontroller.keyboard.KeyboardState
import com.bluetoothcontroller.mouse.MouseState
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class HidReportBuilderTest {

    @Test
    fun `keyboard report empty state produces 8 zero bytes`() {
        val report = HidReportBuilder.buildKeyboardReport(KeyboardState())
        assertArrayEquals(ByteArray(8), report)
    }
    
    @Test
    fun `keyboard report modifier byte is first byte`() {
        val state = KeyboardState(modifiers = KeyboardState.MOD_LEFT_SHIFT)
        val report = HidReportBuilder.buildKeyboardReport(state)
        assertEquals(KeyboardState.MOD_LEFT_SHIFT.toByte(), report[0])
    }
    
    @Test
    fun `keyboard report single key in correct position`() {
        val state = KeyboardState().withKeyPressed(KeyCodes.KEY_A)
        val report = HidReportBuilder.buildKeyboardReport(state)
        assertEquals(KeyCodes.KEY_A.toByte(), report[2])
    }
    
    @Test
    fun `mouse report buttons in first byte`() {
        val state = MouseState(buttons = MouseState.BUTTON_LEFT or MouseState.BUTTON_RIGHT)
        val report = HidReportBuilder.buildMouseReport(state)
        assertEquals((MouseState.BUTTON_LEFT or MouseState.BUTTON_RIGHT).toByte(), report[0])
    }
    
    @Test
    fun `mouse report negative deltas encode correctly`() {
        val state = MouseState(deltaX = -1, deltaY = -2)
        val report = HidReportBuilder.buildMouseReport(state)
        assertEquals(7, report.size)
    }
    
    @Test
    fun `gamepad report center stick maps to 0`() {
        val state = ControllerState(leftStickX = 0f, leftStickY = 0f)
        val report = HidReportBuilder.buildGamepadReport(state)
        assertEquals(13, report.size)
    }
    
    @Test
    fun `gamepad report buttons bitmask correct`() {
        val state = ControllerState().withButton(GameButton.A, true).withButton(GameButton.B, true)
        val report = HidReportBuilder.buildGamepadReport(state)
        assertEquals(13, report.size)
    }
    
    @Test
    fun `gamepad report dpad hat values correct`() {
        val state = ControllerState().withDpad(DpadDirection.UP_RIGHT)
        val report = HidReportBuilder.buildGamepadReport(state)
        assertEquals(13, report.size)
    }
}
