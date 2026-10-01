package com.bluetoothcontroller.mouse

import org.junit.Assert.assertEquals
import org.junit.Test

class MouseMapperTest {

    @Test
    fun `applyMouseSensitivity scales correctly`() {
        assertEquals(10, MouseMapper.applyMouseSensitivity(5f, 2.0f))
        assertEquals(-5, MouseMapper.applyMouseSensitivity(-10f, 0.5f))
    }

    @Test
    fun `applyMouseAcceleration applies factor correctly`() {
        val result = MouseMapper.applyMouseAcceleration(5f, true)
        assertEquals(6.25f, result, 0.01f)
    }
}
