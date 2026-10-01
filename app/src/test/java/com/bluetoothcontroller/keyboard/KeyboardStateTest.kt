package com.bluetoothcontroller.keyboard

import org.junit.Assert.*
import org.junit.Test

class KeyboardStateTest {

    @Test
    fun `default state has no keys`() {
        val state = KeyboardState()
        assertTrue(state.pressedKeys.isEmpty())
        assertEquals(0, state.modifiers)
    }
    
    @Test
    fun `withKeyPressed adds key`() {
        val state = KeyboardState().withKeyPressed(KeyCodes.KEY_A)
        assertTrue(state.pressedKeys.contains(KeyCodes.KEY_A))
    }
    
    @Test
    fun `withKeyReleased removes key`() {
        val state = KeyboardState(pressedKeys = setOf(KeyCodes.KEY_A, KeyCodes.KEY_B))
            .withKeyReleased(KeyCodes.KEY_A)
        assertFalse(state.pressedKeys.contains(KeyCodes.KEY_A))
        assertTrue(state.pressedKeys.contains(KeyCodes.KEY_B))
    }
    
    @Test
    fun `withModifier sets bit`() {
        val state = KeyboardState().withModifier(KeyboardState.MOD_LEFT_SHIFT, true)
        assertEquals(KeyboardState.MOD_LEFT_SHIFT, state.modifiers)
    }
    
    @Test
    fun `withModifier clears bit`() {
        val state = KeyboardState(modifiers = KeyboardState.MOD_LEFT_SHIFT or KeyboardState.MOD_LEFT_CTRL)
            .withModifier(KeyboardState.MOD_LEFT_SHIFT, false)
        assertEquals(KeyboardState.MOD_LEFT_CTRL, state.modifiers)
    }
    
    @Test
    fun `clear returns empty state`() {
        val state = KeyboardState(pressedKeys = setOf(KeyCodes.KEY_A), modifiers = KeyboardState.MOD_LEFT_SHIFT).clear()
        assertTrue(state.pressedKeys.isEmpty())
        assertEquals(0, state.modifiers)
    }
    
    @Test
    fun `multiple keys can be pressed`() {
        val state = KeyboardState()
            .withKeyPressed(KeyCodes.KEY_A)
            .withKeyPressed(KeyCodes.KEY_B)
            .withKeyPressed(KeyCodes.KEY_C)
        assertEquals(3, state.pressedKeys.size)
    }
}
