package com.bluetoothcontroller.keyboard

import org.junit.Assert.assertEquals
import org.junit.Test

class KeyboardMapperTest {

    @Test
    fun `test keycodes mapping`() {
        assertEquals(0x04, KeyCodes.KEY_A)
        assertEquals(0x1D, KeyCodes.KEY_Z)
        assertEquals(0x1E, KeyCodes.KEY_1)
        assertEquals(0x27, KeyCodes.KEY_0)
        assertEquals(0x28, KeyCodes.KEY_ENTER)
        assertEquals(0x2C, KeyCodes.KEY_SPACE)
    }
    
    @Test
    fun `test modifier bitmask`() {
        assertEquals(0x01, KeyboardState.MOD_LEFT_CTRL)
        assertEquals(0x02, KeyboardState.MOD_LEFT_SHIFT)
        assertEquals(0x04, KeyboardState.MOD_LEFT_ALT)
    }
}
