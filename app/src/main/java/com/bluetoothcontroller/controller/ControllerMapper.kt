package com.bluetoothcontroller.controller

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sin
import kotlin.math.PI

/**
 * Handles mapping and filtering of raw inputs (touch/sensors) into structured controller data.
 */
object ControllerMapper {

    /**
     * Applies a deadzone to an input value.
     */
    fun applyDeadZone(value: Float, deadZone: Float): Float {
        if (abs(value) < deadZone) return 0f
        // Scale the remaining range (deadZone -> 1) to (0 -> 1)
        val sign = sign(value)
        return sign * ((abs(value) - deadZone) / (1f - deadZone))
    }

    /**
     * Applies a non-linear response curve to an input.
     */
    fun applyResponseCurve(value: Float, curve: ResponseCurve): Float {
        val sign = sign(value)
        val absVal = abs(value)
        return sign * when (curve) {
            ResponseCurve.LINEAR -> absVal
            ResponseCurve.SQUARED -> absVal.pow(2)
            ResponseCurve.CUBED -> absVal.pow(3)
            ResponseCurve.S_CURVE -> {
                // simple s-curve using sine
                (sin((absVal - 0.5) * PI) + 1.0).toFloat() / 2f
            }
        }
    }

    /**
     * Scales an input by a sensitivity factor, clamping to [-1, 1].
     */
    fun applySensitivity(value: Float, sensitivity: Float): Float {
        return (value * sensitivity).coerceIn(-1f, 1f)
    }

    /**
     * Inverts an input if requested.
     */
    fun applyInversion(value: Float, invert: Boolean): Float {
        return if (invert) -value else value
    }

    /**
     * Processes full joystick input coordinates with config applied.
     */
    fun processJoystickInput(rawX: Float, rawY: Float, config: JoystickConfig): Pair<Float, Float> {
        val xZoned = applyDeadZone(rawX, config.deadZone)
        val yZoned = applyDeadZone(rawY, config.deadZone)
        
        val xCurve = applyResponseCurve(xZoned, config.responseCurve)
        val yCurve = applyResponseCurve(yZoned, config.responseCurve)
        
        val xSens = applySensitivity(xCurve, config.sensitivity)
        val ySens = applySensitivity(yCurve, config.sensitivity)
        
        val xFinal = applyInversion(xSens, config.invertX)
        val yFinal = applyInversion(ySens, config.invertY)
        
        return Pair(xFinal, yFinal)
    }

    /**
     * Processes trigger inputs with config applied.
     */
    fun processTriggerInput(rawValue: Float, config: TriggerConfig): Float {
        if (!config.isAnalog) {
            return if (rawValue > 0.5f) 1f else 0f
        }
        return applySensitivity(rawValue, config.sensitivity).coerceIn(0f, 1f)
    }
}
