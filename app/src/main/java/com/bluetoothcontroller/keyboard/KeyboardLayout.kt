package com.bluetoothcontroller.keyboard

import com.bluetoothcontroller.keyboard.KeyboardMapper.KeyCodes
import com.bluetoothcontroller.keyboard.KeyboardState.Companion.MOD_LEFT_CTRL
import com.bluetoothcontroller.keyboard.KeyboardState.Companion.MOD_LEFT_SHIFT
import com.bluetoothcontroller.keyboard.KeyboardState.Companion.MOD_LEFT_ALT

/**
 * Definition of a single key on a keyboard layout.
 */
data class KeyDef(
    val label: String,
    val shiftLabel: String? = null,
    val keyCode: Int,
    val modifier: Int? = null,
    val width: Float = 1.0f,
    val isSpecial: Boolean = false
)

object KeyboardLayout {
    
    val QWERTY_LAYOUT: List<List<KeyDef>> = listOf(
        listOf(
            KeyDef("`", "~", KeyCodes.KEY_GRAVE), KeyDef("1", "!", KeyCodes.KEY_1), KeyDef("2", "@", KeyCodes.KEY_2),
            KeyDef("3", "#", KeyCodes.KEY_3), KeyDef("4", "$", KeyCodes.KEY_4), KeyDef("5", "%", KeyCodes.KEY_5),
            KeyDef("6", "^", KeyCodes.KEY_6), KeyDef("7", "&", KeyCodes.KEY_7), KeyDef("8", "*", KeyCodes.KEY_8),
            KeyDef("9", "(", KeyCodes.KEY_9), KeyDef("0", ")", KeyCodes.KEY_0), KeyDef("-", "_", KeyCodes.KEY_MINUS),
            KeyDef("=", "+", KeyCodes.KEY_EQUAL), KeyDef("Backspace", null, KeyCodes.KEY_BACKSPACE, width = 1.5f, isSpecial = true)
        ),
        listOf(
            KeyDef("Tab", null, KeyCodes.KEY_TAB, width = 1.5f, isSpecial = true), KeyDef("Q", null, KeyCodes.KEY_Q),
            KeyDef("W", null, KeyCodes.KEY_W), KeyDef("E", null, KeyCodes.KEY_E), KeyDef("R", null, KeyCodes.KEY_R),
            KeyDef("T", null, KeyCodes.KEY_T), KeyDef("Y", null, KeyCodes.KEY_Y), KeyDef("U", null, KeyCodes.KEY_U),
            KeyDef("I", null, KeyCodes.KEY_I), KeyDef("O", null, KeyCodes.KEY_O), KeyDef("P", null, KeyCodes.KEY_P),
            KeyDef("[", "{", KeyCodes.KEY_LEFT_BRACKET), KeyDef("]", "}", KeyCodes.KEY_RIGHT_BRACKET), KeyDef("\\", "|", KeyCodes.KEY_BACKSLASH)
        ),
        listOf(
            KeyDef("Caps", null, KeyCodes.KEY_CAPS_LOCK, width = 1.8f, isSpecial = true), KeyDef("A", null, KeyCodes.KEY_A),
            KeyDef("S", null, KeyCodes.KEY_S), KeyDef("D", null, KeyCodes.KEY_D), KeyDef("F", null, KeyCodes.KEY_F),
            KeyDef("G", null, KeyCodes.KEY_G), KeyDef("H", null, KeyCodes.KEY_H), KeyDef("J", null, KeyCodes.KEY_J),
            KeyDef("K", null, KeyCodes.KEY_K), KeyDef("L", null, KeyCodes.KEY_L), KeyDef(";", ":", KeyCodes.KEY_SEMICOLON),
            KeyDef("'", "\"", KeyCodes.KEY_APOSTROPHE), KeyDef("Enter", null, KeyCodes.KEY_ENTER, width = 2.2f, isSpecial = true)
        ),
        listOf(
            KeyDef("Shift", null, 0, modifier = MOD_LEFT_SHIFT, width = 2.4f, isSpecial = true), KeyDef("Z", null, KeyCodes.KEY_Z),
            KeyDef("X", null, KeyCodes.KEY_X), KeyDef("C", null, KeyCodes.KEY_C), KeyDef("V", null, KeyCodes.KEY_V),
            KeyDef("B", null, KeyCodes.KEY_B), KeyDef("N", null, KeyCodes.KEY_N), KeyDef("M", null, KeyCodes.KEY_M),
            KeyDef(",", "<", KeyCodes.KEY_COMMA), KeyDef(".", ">", KeyCodes.KEY_PERIOD), KeyDef("/", "?", KeyCodes.KEY_SLASH),
            KeyDef("Shift", null, 0, modifier = MOD_LEFT_SHIFT, width = 2.4f, isSpecial = true)
        ),
        listOf(
            KeyDef("Ctrl", null, 0, modifier = MOD_LEFT_CTRL, width = 1.5f, isSpecial = true),
            KeyDef("Alt", null, 0, modifier = MOD_LEFT_ALT, width = 1.5f, isSpecial = true),
            KeyDef("Space", null, KeyCodes.KEY_SPACE, width = 6.0f),
            KeyDef("Alt", null, 0, modifier = MOD_LEFT_ALT, width = 1.5f, isSpecial = true),
            KeyDef("Ctrl", null, 0, modifier = MOD_LEFT_CTRL, width = 1.5f, isSpecial = true)
        )
    )

    val GAMING_LAYOUT: List<List<KeyDef>> = listOf(
        listOf(
            KeyDef("Esc", null, KeyCodes.KEY_ESCAPE, isSpecial = true), KeyDef("1", null, KeyCodes.KEY_1),
            KeyDef("2", null, KeyCodes.KEY_2), KeyDef("3", null, KeyCodes.KEY_3), KeyDef("4", null, KeyCodes.KEY_4),
            KeyDef("5", null, KeyCodes.KEY_5)
        ),
        listOf(
            KeyDef("Tab", null, KeyCodes.KEY_TAB, isSpecial = true), KeyDef("Q", null, KeyCodes.KEY_Q),
            KeyDef("W", null, KeyCodes.KEY_W), KeyDef("E", null, KeyCodes.KEY_E), KeyDef("R", null, KeyCodes.KEY_R)
        ),
        listOf(
            KeyDef("Shift", null, 0, modifier = MOD_LEFT_SHIFT, width = 1.5f, isSpecial = true), KeyDef("A", null, KeyCodes.KEY_A),
            KeyDef("S", null, KeyCodes.KEY_S), KeyDef("D", null, KeyCodes.KEY_D), KeyDef("F", null, KeyCodes.KEY_F)
        ),
        listOf(
            KeyDef("Ctrl", null, 0, modifier = MOD_LEFT_CTRL, isSpecial = true), KeyDef("Z", null, KeyCodes.KEY_Z),
            KeyDef("X", null, KeyCodes.KEY_X), KeyDef("C", null, KeyCodes.KEY_C), KeyDef("V", null, KeyCodes.KEY_V)
        ),
        listOf(
            KeyDef("Space", null, KeyCodes.KEY_SPACE, width = 4.0f)
        )
    )

    val FUNCTION_ROW: List<KeyDef> = listOf(
        KeyDef("F1", null, KeyCodes.KEY_F1), KeyDef("F2", null, KeyCodes.KEY_F2),
        KeyDef("F3", null, KeyCodes.KEY_F3), KeyDef("F4", null, KeyCodes.KEY_F4),
        KeyDef("F5", null, KeyCodes.KEY_F5), KeyDef("F6", null, KeyCodes.KEY_F6),
        KeyDef("F7", null, KeyCodes.KEY_F7), KeyDef("F8", null, KeyCodes.KEY_F8),
        KeyDef("F9", null, KeyCodes.KEY_F9), KeyDef("F10", null, KeyCodes.KEY_F10),
        KeyDef("F11", null, KeyCodes.KEY_F11), KeyDef("F12", null, KeyCodes.KEY_F12)
    )

    val NAVIGATION_KEYS: List<KeyDef> = listOf(
        KeyDef("Insert", null, KeyCodes.KEY_INSERT), KeyDef("Home", null, KeyCodes.KEY_HOME), KeyDef("PgUp", null, KeyCodes.KEY_PAGE_UP),
        KeyDef("Delete", null, KeyCodes.KEY_DELETE), KeyDef("End", null, KeyCodes.KEY_END), KeyDef("PgDn", null, KeyCodes.KEY_PAGE_DOWN),
        KeyDef("↑", null, KeyCodes.KEY_UP), KeyDef("↓", null, KeyCodes.KEY_DOWN),
        KeyDef("←", null, KeyCodes.KEY_LEFT), KeyDef("→", null, KeyCodes.KEY_RIGHT)
    )
}
