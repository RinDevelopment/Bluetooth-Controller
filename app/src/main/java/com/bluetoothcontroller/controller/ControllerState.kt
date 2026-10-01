package com.bluetoothcontroller.controller

/**
 * Represents the buttons on a game controller.
 */
enum class GameButton {
    A, B, X, Y,
    L1, R1, L2_DIGITAL, R2_DIGITAL,
    START, SELECT,
    LEFT_STICK_PRESS, RIGHT_STICK_PRESS
}

/**
 * Represents D-pad directions including diagonals.
 */
enum class DpadDirection(val hatValue: Int) {
    NONE(-1),
    UP(0), UP_RIGHT(1), RIGHT(2), DOWN_RIGHT(3),
    DOWN(4), DOWN_LEFT(5), LEFT(6), UP_LEFT(7)
}

/**
 * Represents the complete state of a game controller.
 * 
 * Stick values range from -1.0 to 1.0.
 * Trigger values range from 0.0 to 1.0.
 */
data class ControllerState(
    val leftStickX: Float = 0f,
    val leftStickY: Float = 0f,
    val rightStickX: Float = 0f,
    val rightStickY: Float = 0f,
    val leftTrigger: Float = 0f,
    val rightTrigger: Float = 0f,
    val buttons: Set<GameButton> = emptySet(),
    val dpadDirection: DpadDirection = DpadDirection.NONE
) {
    fun isButtonPressed(button: GameButton): Boolean = button in buttons
    
    fun withButton(button: GameButton, pressed: Boolean): ControllerState {
        return if (pressed) copy(buttons = buttons + button)
        else copy(buttons = buttons - button)
    }
    
    fun withDpad(direction: DpadDirection): ControllerState = copy(dpadDirection = direction)
    
    fun withLeftStick(x: Float, y: Float): ControllerState = copy(
        leftStickX = x.coerceIn(-1f, 1f),
        leftStickY = y.coerceIn(-1f, 1f)
    )
    
    fun withRightStick(x: Float, y: Float): ControllerState = copy(
        rightStickX = x.coerceIn(-1f, 1f),
        rightStickY = y.coerceIn(-1f, 1f)
    )
    
    fun withLeftTrigger(value: Float): ControllerState = copy(leftTrigger = value.coerceIn(0f, 1f))
    fun withRightTrigger(value: Float): ControllerState = copy(rightTrigger = value.coerceIn(0f, 1f))
    
    companion object {
        val EMPTY = ControllerState()
    }
}
