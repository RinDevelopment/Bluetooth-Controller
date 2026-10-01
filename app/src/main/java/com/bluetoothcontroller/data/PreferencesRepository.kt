package com.bluetoothcontroller.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesRepository(private val context: Context) {

    companion object {
        val THEME_KEY = stringPreferencesKey("theme")
        val UI_SCALE_KEY = floatPreferencesKey("ui_scale")
        val SELECTED_ROLE_KEY = stringPreferencesKey("selected_role")
        // Define other keys...

        @Volatile
        private var INSTANCE: PreferencesRepository? = null

        fun getInstance(context: Context): PreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferencesRepository(context).also { INSTANCE = it }
            }
        }
    }

    fun getSettings(): Flow<AppSettings> {
        return context.dataStore.data.map { preferences ->
            AppSettings(
                theme = AppTheme.valueOf(preferences[THEME_KEY] ?: AppTheme.DARK.name),
                uiScale = preferences[UI_SCALE_KEY] ?: 1.0f,
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
        }
    }

    suspend fun updateSettings(settings: AppSettings) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = settings.theme.name
            preferences[UI_SCALE_KEY] = settings.uiScale
            // Update other properties...
        }
    }

    fun getSelectedRole(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[SELECTED_ROLE_KEY]
        }
    }

    suspend fun setSelectedRole(role: String) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_ROLE_KEY] = role
        }
    }
}
