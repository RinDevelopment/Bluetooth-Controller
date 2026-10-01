package com.bluetoothcontroller.mouse

import org.junit.Assert.*
import org.junit.Test

class MouseStateTest {

    @Test
    fun `default state has no movement or buttons`() {
        val state = MouseState()
        assertEquals(0, state.deltaX)
        assertEquals(0, state.deltaY)
        assertEquals(0, state.buttons)
        assertEquals(0, state.wheelDelta)
    }
    
    @Test
    fun `withButton sets and clears button bits correctly`() {
        var state = MouseState().withButton(MouseState.BUTTON_LEFT, true)
        assertTrue(state.isLeftPressed())
        
        state = state.withButton(MouseState.BUTTON_RIGHT, true)
        assertTrue(state.isRightPressed())
        
        state = state.withButton(MouseState.BUTTON_LEFT, false)
        assertFalse(state.isLeftPressed())
        assertTrue(state.isRightPressed())
    }
    
    @Test
    fun `withMovement sets deltas`() {
        val state = MouseState().withMovement(10, -5)
        assertEquals(10, state.deltaX)
        assertEquals(-5, state.deltaY)
    }
    
    @Test
    fun `withWheel sets wheel`() {
        val state = MouseState().withWheel(3)
        assertEquals(3, state.wheelDelta)
    }
    
    @Test
    fun `clearMovement zeros movement but preserves buttons`() {
        val state = MouseState(deltaX = 5, deltaY = 10, buttons = MouseState.BUTTON_LEFT, wheelDelta = 2)
            .clearMovement()
        assertEquals(0, state.deltaX)
        assertEquals(0, state.deltaY)
        assertEquals(0, state.wheelDelta)
        assertTrue(state.isLeftPressed())
    }
    
    @Test
    fun `button bitmask combinations`() {
        val state = MouseState().withButton(MouseState.BUTTON_LEFT, true).withButton(MouseState.BUTTON_RIGHT, true)
        assertEquals(MouseState.BUTTON_LEFT or MouseState.BUTTON_RIGHT, state.buttons)
    }
}
