package com.bluetoothcontroller.controller

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.abs

class ControllerMapperTest {

    @Test
    fun `dead zone values within are 0`() {
        assertEquals(0f, applyDeadZone(0.05f, 0.1f), 0.001f)
        assertEquals(0f, applyDeadZone(-0.05f, 0.1f), 0.001f)
    }

    @Test
    fun `dead zone values outside are scaled`() {
        // formula: (abs(val) - deadzone) / (1 - deadzone) * sign(val)
        assertEquals(0.5f, applyDeadZone(0.55f, 0.1f), 0.001f)
        assertEquals(-0.5f, applyDeadZone(-0.55f, 0.1f), 0.001f)
    }

    @Test
    fun `response curves linear`() {
        assertEquals(0.5f, applyResponseCurve(0.5f, ResponseCurve.LINEAR), 0.001f)
        assertEquals(-0.5f, applyResponseCurve(-0.5f, ResponseCurve.LINEAR), 0.001f)
    }

    @Test
    fun `response curves squared`() {
        assertEquals(0.25f, applyResponseCurve(0.5f, ResponseCurve.SQUARED), 0.001f)
        assertEquals(-0.25f, applyResponseCurve(-0.5f, ResponseCurve.SQUARED), 0.001f)
    }

    @Test
    fun `response curves cubed`() {
        assertEquals(0.125f, applyResponseCurve(0.5f, ResponseCurve.CUBED), 0.001f)
        assertEquals(-0.125f, applyResponseCurve(-0.5f, ResponseCurve.CUBED), 0.001f)
    }
}
