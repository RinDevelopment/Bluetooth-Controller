package com.bluetoothcontroller.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bluetoothcontroller.data.AppSettings
import com.bluetoothcontroller.data.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val _settings = MutableStateFlow(
        AppSettings(
            theme = AppTheme.DARK,
            uiScale = 1.0f,
            transparency = 1.0f,
            hapticFeedback = true,
            vibrationIntensity = 1.0f,
            autoReconnect = true,
            deviceName = "BT Controller",
            mouseSensitivity = 1.0f,
            mouseAcceleration = true,
            scrollSpeed = 1.0f,
            invertScrolling = false,
            keyRepeatEnabled = true,
            keyRepeatDelay = 500L,
            keyRepeatRate = 50L,
            keySize = 1.0f,
            keyHapticFeedback = true,
            controllerButtonSize = 1.0f,
            joystickSize = 1.0f,
            deadZone = 0.1f,
            stickSensitivity = 1.0f,
            showLatencyIndicator = false,
            selectedProfileId = ""
        )
    )
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    fun updateSetting(update: AppSettings.() -> AppSettings) {
        viewModelScope.launch {
            _settings.value = _settings.value.update()
            // Persist using PreferencesRepository
        }
    }
}
