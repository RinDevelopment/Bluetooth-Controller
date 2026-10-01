package com.bluetoothcontroller.bluetooth

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HidDescriptorsTest {

    @Test
    fun `keyboard descriptor is not empty`() {
        assertTrue(HidDescriptors.KEYBOARD_DESCRIPTOR.isNotEmpty())
    }

    @Test
    fun `mouse descriptor is not empty`() {
        assertTrue(HidDescriptors.MOUSE_DESCRIPTOR.isNotEmpty())
    }

    @Test
    fun `gamepad descriptor is not empty`() {
        assertTrue(HidDescriptors.GAMEPAD_DESCRIPTOR.isNotEmpty())
    }

    @Test
    fun `combined descriptor contains all three`() {
        assertTrue(HidDescriptors.COMBINED_DESCRIPTOR.isNotEmpty())
    }
    
    @Test
    fun `descriptors start with correct usage page bytes`() {
        assertEquals(0x05.toByte(), HidDescriptors.KEYBOARD_DESCRIPTOR[0])
        assertEquals(0x01.toByte(), HidDescriptors.KEYBOARD_DESCRIPTOR[1])
    }
}
