package com.bluetoothcontroller.mouse

/**
 * Represents the state of the virtual mouse.
 * 
 * @param deltaX Horizontal movement delta (positive = right)
 * @param deltaY Vertical movement delta (positive = down)
 * @param buttons Bitmask of pressed buttons:
 *   bit 0: Left button
 *   bit 1: Right button
 *   bit 2: Middle button
 * @param wheelDelta Scroll wheel delta (positive = scroll up)
 */
data class MouseState(
    val deltaX: Int = 0,
    val deltaY: Int = 0,
    val buttons: Int = 0,
    val wheelDelta: Int = 0
) {
    val isLeftPressed: Boolean get() = buttons and BUTTON_LEFT != 0
    val isRightPressed: Boolean get() = buttons and BUTTON_RIGHT != 0
    val isMiddlePressed: Boolean get() = buttons and BUTTON_MIDDLE != 0
    
    fun withButton(button: Int, pressed: Boolean): MouseState {
        return if (pressed) copy(buttons = buttons or button)
        else copy(buttons = buttons and button.inv())
    }
    
    fun withMovement(dx: Int, dy: Int): MouseState = copy(deltaX = dx, deltaY = dy)
    
    fun withWheel(delta: Int): MouseState = copy(wheelDelta = delta)
    
    fun clearMovement(): MouseState = copy(deltaX = 0, deltaY = 0, wheelDelta = 0)
    
    companion object {
        const val BUTTON_LEFT = 0x01
        const val BUTTON_RIGHT = 0x02
        const val BUTTON_MIDDLE = 0x04
        
        val EMPTY = MouseState()
    }
}
