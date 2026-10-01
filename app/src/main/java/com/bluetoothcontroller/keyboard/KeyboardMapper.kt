package com.bluetoothcontroller.keyboard

/**
 * Maps characters and layout keys to HID Keyboard Usage IDs.
 */
object KeyboardMapper {

    object KeyCodes {
        const val KEY_A = 0x04
        const val KEY_B = 0x05
        const val KEY_C = 0x06
        const val KEY_D = 0x07
        const val KEY_E = 0x08
        const val KEY_F = 0x09
        const val KEY_G = 0x0A
        const val KEY_H = 0x0B
        const val KEY_I = 0x0C
        const val KEY_J = 0x0D
        const val KEY_K = 0x0E
        const val KEY_L = 0x0F
        const val KEY_M = 0x10
        const val KEY_N = 0x11
        const val KEY_O = 0x12
        const val KEY_P = 0x13
        const val KEY_Q = 0x14
        const val KEY_R = 0x15
        const val KEY_S = 0x16
        const val KEY_T = 0x17
        const val KEY_U = 0x18
        const val KEY_V = 0x19
        const val KEY_W = 0x1A
        const val KEY_X = 0x1B
        const val KEY_Y = 0x1C
        const val KEY_Z = 0x1D

        const val KEY_1 = 0x1E
        const val KEY_2 = 0x1F
        const val KEY_3 = 0x20
        const val KEY_4 = 0x21
        const val KEY_5 = 0x22
        const val KEY_6 = 0x23
        const val KEY_7 = 0x24
        const val KEY_8 = 0x25
        const val KEY_9 = 0x26
        const val KEY_0 = 0x27

        const val KEY_ENTER = 0x28
        const val KEY_ESCAPE = 0x29
        const val KEY_BACKSPACE = 0x2A
        const val KEY_TAB = 0x2B
        const val KEY_SPACE = 0x2C
        
        const val KEY_MINUS = 0x2D
        const val KEY_EQUAL = 0x2E
        const val KEY_LEFT_BRACKET = 0x2F
        const val KEY_RIGHT_BRACKET = 0x30
        const val KEY_BACKSLASH = 0x31
        const val KEY_SEMICOLON = 0x33
        const val KEY_APOSTROPHE = 0x34
        const val KEY_GRAVE = 0x35
        const val KEY_COMMA = 0x36
        const val KEY_PERIOD = 0x37
        const val KEY_SLASH = 0x38
        const val KEY_CAPS_LOCK = 0x39

        const val KEY_F1 = 0x3A
        const val KEY_F2 = 0x3B
        const val KEY_F3 = 0x3C
        const val KEY_F4 = 0x3D
        const val KEY_F5 = 0x3E
        const val KEY_F6 = 0x3F
        const val KEY_F7 = 0x40
        const val KEY_F8 = 0x41
        const val KEY_F9 = 0x42
        const val KEY_F10 = 0x43
        const val KEY_F11 = 0x44
        const val KEY_F12 = 0x45

        const val KEY_PRINT_SCREEN = 0x46
        const val KEY_SCROLL_LOCK = 0x47
        const val KEY_PAUSE = 0x48
        const val KEY_INSERT = 0x49
        const val KEY_HOME = 0x4A
        const val KEY_PAGE_UP = 0x4B
        const val KEY_DELETE = 0x4C
        const val KEY_END = 0x4D
        const val KEY_PAGE_DOWN = 0x4E

        const val KEY_RIGHT = 0x4F
        const val KEY_LEFT = 0x50
        const val KEY_DOWN = 0x51
        const val KEY_UP = 0x52
    }

    /**
     * Converts a single character into a HID KeyCode and a boolean indicating if Shift is needed.
     */
    fun charToHidKeyCode(char: Char): Pair<Int, Boolean> {
        return when (char) {
            in 'a'..'z' -> Pair(KeyCodes.KEY_A + (char - 'a'), false)
            in 'A'..'Z' -> Pair(KeyCodes.KEY_A + (char - 'A'), true)
            '1' -> Pair(KeyCodes.KEY_1, false); '!' -> Pair(KeyCodes.KEY_1, true)
            '2' -> Pair(KeyCodes.KEY_2, false); '@' -> Pair(KeyCodes.KEY_2, true)
            '3' -> Pair(KeyCodes.KEY_3, false); '#' -> Pair(KeyCodes.KEY_3, true)
            '4' -> Pair(KeyCodes.KEY_4, false); '$' -> Pair(KeyCodes.KEY_4, true)
            '5' -> Pair(KeyCodes.KEY_5, false); '%' -> Pair(KeyCodes.KEY_5, true)
            '6' -> Pair(KeyCodes.KEY_6, false); '^' -> Pair(KeyCodes.KEY_6, true)
            '7' -> Pair(KeyCodes.KEY_7, false); '&' -> Pair(KeyCodes.KEY_7, true)
            '8' -> Pair(KeyCodes.KEY_8, false); '*' -> Pair(KeyCodes.KEY_8, true)
            '9' -> Pair(KeyCodes.KEY_9, false); '(' -> Pair(KeyCodes.KEY_9, true)
            '0' -> Pair(KeyCodes.KEY_0, false); ')' -> Pair(KeyCodes.KEY_0, true)
            '-' -> Pair(KeyCodes.KEY_MINUS, false); '_' -> Pair(KeyCodes.KEY_MINUS, true)
            '=' -> Pair(KeyCodes.KEY_EQUAL, false); '+' -> Pair(KeyCodes.KEY_EQUAL, true)
            '[' -> Pair(KeyCodes.KEY_LEFT_BRACKET, false); '{' -> Pair(KeyCodes.KEY_LEFT_BRACKET, true)
            ']' -> Pair(KeyCodes.KEY_RIGHT_BRACKET, false); '}' -> Pair(KeyCodes.KEY_RIGHT_BRACKET, true)
            '\\' -> Pair(KeyCodes.KEY_BACKSLASH, false); '|' -> Pair(KeyCodes.KEY_BACKSLASH, true)
            ';' -> Pair(KeyCodes.KEY_SEMICOLON, false); ':' -> Pair(KeyCodes.KEY_SEMICOLON, true)
            '\'' -> Pair(KeyCodes.KEY_APOSTROPHE, false); '"' -> Pair(KeyCodes.KEY_APOSTROPHE, true)
            '`' -> Pair(KeyCodes.KEY_GRAVE, false); '~' -> Pair(KeyCodes.KEY_GRAVE, true)
            ',' -> Pair(KeyCodes.KEY_COMMA, false); '<' -> Pair(KeyCodes.KEY_COMMA, true)
            '.' -> Pair(KeyCodes.KEY_PERIOD, false); '>' -> Pair(KeyCodes.KEY_PERIOD, true)
            '/' -> Pair(KeyCodes.KEY_SLASH, false); '?' -> Pair(KeyCodes.KEY_SLASH, true)
            ' ' -> Pair(KeyCodes.KEY_SPACE, false)
            '\n' -> Pair(KeyCodes.KEY_ENTER, false)
            '\t' -> Pair(KeyCodes.KEY_TAB, false)
            else -> Pair(0, false)
        }
    }
}
