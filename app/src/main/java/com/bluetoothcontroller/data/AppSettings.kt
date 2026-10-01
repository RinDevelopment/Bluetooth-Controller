package com.bluetoothcontroller.data

/**
 * Global application settings state.
 */
data class AppSettings(
    val theme: AppTheme = AppTheme.DARK,
    val uiScale: Float = 1f,
    val transparency: Float = 0.9f,
    val hapticFeedback: Boolean = true,
    val vibrationIntensity: Float = 0.5f,
    val autoReconnect: Boolean = true,
    val deviceName: String = "BT Controller",
    val mouseSensitivity: Float = 1f,
    val mouseAcceleration: Boolean = true,
    val scrollSpeed: Float = 1f,
    val invertScrolling: Boolean = false,
    val keyRepeatEnabled: Boolean = true,
    val keyRepeatDelay: Long = 500L,
    val keyRepeatRate: Long = 50L,
    val keySize: Float = 1f,
    val keyHapticFeedback: Boolean = true,
    val controllerButtonSize: Float = 1f,
    val joystickSize: Float = 1f,
    val deadZone: Float = 0.1f,
    val stickSensitivity: Float = 1f,
    val showLatencyIndicator: Boolean = false,
    val selectedProfileId: String = "default"
)

enum class AppTheme {
    LIGHT, DARK, SYSTEM
}
