package com.bluetoothcontroller.mouse

import org.junit.Assert.assertEquals
import org.junit.Test

class MouseMapperTest {

    @Test
    fun `applyMouseSensitivity scales correctly`() {
        assertEquals(10, applyMouseSensitivity(5, 2.0f))
        assertEquals(-5, applyMouseSensitivity(-10, 0.5f))
    }

    @Test
    fun `applyMouseAcceleration applies factor correctly`() {
        assertEquals(12, applyMouseAcceleration(5, 1.5f))
    }
}
