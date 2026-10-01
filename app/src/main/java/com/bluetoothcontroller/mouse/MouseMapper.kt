package com.bluetoothcontroller.mouse

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sign

/**
 * Handles mapping raw pointer events into discrete mouse inputs.
 */
object MouseMapper {

    fun applyMouseSensitivity(delta: Float, sensitivity: Float): Int {
        return (delta * sensitivity).roundToInt()
    }

    fun applyMouseAcceleration(delta: Float, acceleration: Boolean): Float {
        if (!acceleration) return delta
        // Simple acceleration curve
        val absVal = abs(delta)
        return sign(delta) * (absVal * (1.0f + absVal * 0.05f))
    }

    fun processMouseMovement(
        rawDx: Float, 
        rawDy: Float, 
        sensitivity: Float, 
        acceleration: Boolean, 
        invertY: Boolean
    ): Pair<Int, Int> {
        val dxAccel = applyMouseAcceleration(rawDx, acceleration)
        val dyAccel = applyMouseAcceleration(rawDy, acceleration)
        
        val dxFinal = applyMouseSensitivity(dxAccel, sensitivity)
        val dySens = applyMouseSensitivity(dyAccel, sensitivity)
        
        val dyFinal = if (invertY) -dySens else dySens
        
        return Pair(dxFinal, dyFinal)
    }

    fun processScrollInput(rawDelta: Float, scrollSpeed: Float, invertScroll: Boolean): Int {
        val delta = rawDelta * scrollSpeed
        val finalDelta = if (invertScroll) -delta else delta
        return finalDelta.roundToInt()
    }
}
