package com.bluetoothcontroller.keyboard

/**
 * Represents the state of the virtual keyboard.
 * 
 * @param pressedKeys Set of HID Usage IDs (from HID Usage Tables, Keyboard page 0x07)
 * @param modifiers Bitmask of modifier keys:
 *   bit 0: Left Control
 *   bit 1: Left Shift
 *   bit 2: Left Alt
 *   bit 3: Left GUI (Windows/Command)
 *   bit 4: Right Control
 *   bit 5: Right Shift
 *   bit 6: Right Alt
 *   bit 7: Right GUI
 */
data class KeyboardState(
    val pressedKeys: Set<Int> = emptySet(),
    val modifiers: Int = 0
) {
    fun withKeyPressed(keyCode: Int): KeyboardState = copy(pressedKeys = pressedKeys + keyCode)
    
    fun withKeyReleased(keyCode: Int): KeyboardState = copy(pressedKeys = pressedKeys - keyCode)
    
    fun withModifier(modifier: Int, pressed: Boolean): KeyboardState {
        return if (pressed) copy(modifiers = modifiers or modifier)
        else copy(modifiers = modifiers and modifier.inv())
    }
    
    fun clear(): KeyboardState = KeyboardState()
    
    companion object {
        const val MOD_LEFT_CTRL = 0x01
        const val MOD_LEFT_SHIFT = 0x02
        const val MOD_LEFT_ALT = 0x04
        const val MOD_LEFT_GUI = 0x08
        const val MOD_RIGHT_CTRL = 0x10
        const val MOD_RIGHT_SHIFT = 0x20
        const val MOD_RIGHT_ALT = 0x40
        const val MOD_RIGHT_GUI = 0x80
        
        val EMPTY = KeyboardState()
    }
}
