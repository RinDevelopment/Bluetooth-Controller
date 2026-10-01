package com.bluetoothcontroller.bluetooth

import org.junit.Assert.assertTrue
import org.junit.Test

class HidDescriptorsTest {

    @Test
    fun `keyboard descriptor is not empty`() {
        assertTrue(KEYBOARD_DESCRIPTOR.isNotEmpty())
    }

    @Test
    fun `mouse descriptor is not empty`() {
        assertTrue(MOUSE_DESCRIPTOR.isNotEmpty())
    }

    @Test
    fun `gamepad descriptor is not empty`() {
        assertTrue(GAMEPAD_DESCRIPTOR.isNotEmpty())
    }

    @Test
    fun `combined descriptor contains all three`() {
        assertTrue(COMBINED_DESCRIPTOR.isNotEmpty())
    }
    
    @Test
    fun `descriptors start with correct usage page bytes`() {
        assertEquals(0x05.toByte(), KEYBOARD_DESCRIPTOR[0])
        assertEquals(0x01.toByte(), KEYBOARD_DESCRIPTOR[1])
    }
}
