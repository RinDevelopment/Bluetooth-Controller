package com.bluetoothcontroller.controller

import org.junit.Assert.*
import org.junit.Test

class ControllerStateTest {
    @Test
    fun `default state has no buttons pressed`() {
        val state = ControllerState()
        assertTrue(state.buttons.isEmpty())
        assertEquals(DpadDirection.NONE, state.dpadDirection)
    }
    
    @Test
    fun `withButton adds button`() {
        val state = ControllerState().withButton(GameButton.A, true)
        assertTrue(state.isButtonPressed(GameButton.A))
        assertFalse(state.isButtonPressed(GameButton.B))
    }
    
    @Test
    fun `withButton removes button`() {
        val state = ControllerState(buttons = setOf(GameButton.A, GameButton.B))
            .withButton(GameButton.A, false)
        assertFalse(state.isButtonPressed(GameButton.A))
        assertTrue(state.isButtonPressed(GameButton.B))
    }
    
    @Test
    fun `stick values are clamped`() {
        val state = ControllerState().withLeftStick(2f, -3f)
        assertEquals(1f, state.leftStickX, 0.001f)
        assertEquals(-1f, state.leftStickY, 0.001f)
    }
    
    @Test
    fun `trigger values are clamped`() {
        val state = ControllerState().withLeftTrigger(1.5f)
        assertEquals(1f, state.leftTrigger, 0.001f)
        val state2 = ControllerState().withRightTrigger(-0.5f)
        assertEquals(0f, state2.rightTrigger, 0.001f)
    }
    
    @Test
    fun `withDpad changes direction`() {
        val state = ControllerState().withDpad(DpadDirection.UP_RIGHT)
        assertEquals(DpadDirection.UP_RIGHT, state.dpadDirection)
    }
    
    @Test
    fun `multiple buttons can be pressed`() {
        val state = ControllerState()
            .withButton(GameButton.A, true)
            .withButton(GameButton.B, true)
            .withButton(GameButton.X, true)
        assertEquals(3, state.buttons.size)
    }
}
