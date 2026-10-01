package com.bluetoothcontroller.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bluetoothcontroller.data.AppSettings
import com.bluetoothcontroller.data.AppTheme
import com.bluetoothcontroller.ui.viewmodels.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onNavigateBack: () -> Unit
) {
    val settings by viewModel.settings.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(androidx.compose.material.icons.Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                SettingsSectionTitle("Controller")
                SettingsSlider(
                    label = "Button Size",
                    value = settings.controllerButtonSize,
                    onValueChange = { viewModel.updateSetting { copy(controllerButtonSize = it) } },
                    valueRange = 0.5f..2.0f
                )
                SettingsSlider(
                    label = "Joystick Size",
                    value = settings.joystickSize,
                    onValueChange = { viewModel.updateSetting { copy(joystickSize = it) } },
                    valueRange = 0.5f..2.0f
                )
                SettingsSlider(
                    label = "Dead Zone",
                    value = settings.deadZone,
                    onValueChange = { viewModel.updateSetting { copy(deadZone = it) } },
                    valueRange = 0.0f..0.5f
                )
                SettingsSlider(
                    label = "Stick Sensitivity",
                    value = settings.stickSensitivity,
                    onValueChange = { viewModel.updateSetting { copy(stickSensitivity = it) } },
                    valueRange = 0.1f..2.0f
                )
                SettingsSwitch(
                    label = "Haptic Feedback",
                    checked = settings.hapticFeedback,
                    onCheckedChange = { viewModel.updateSetting { copy(hapticFeedback = it) } }
                )
            }

            item {
                SettingsSectionTitle("Mouse")
                SettingsSlider(
                    label = "Sensitivity",
                    value = settings.mouseSensitivity,
                    onValueChange = { viewModel.updateSetting { copy(mouseSensitivity = it) } },
                    valueRange = 0.1f..5.0f
                )
                SettingsSwitch(
                    label = "Acceleration",
                    checked = settings.mouseAcceleration,
                    onCheckedChange = { viewModel.updateSetting { copy(mouseAcceleration = it) } }
                )
            }

            item {
                SettingsSectionTitle("Appearance")
                Text("Theme", modifier = Modifier.padding(bottom = 8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ThemeOption(AppTheme.LIGHT, settings.theme, onSelect = { viewModel.updateSetting { copy(theme = it) } })
                    ThemeOption(AppTheme.DARK, settings.theme, onSelect = { viewModel.updateSetting { copy(theme = it) } })
                    ThemeOption(AppTheme.SYSTEM, settings.theme, onSelect = { viewModel.updateSetting { copy(theme = it) } })
                }
            }

            item {
                SettingsSectionTitle("Privacy")
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Your input stays on your devices. No data is collected or transmitted over the internet.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun SettingsSlider(label: String, value: Float, onValueChange: (Float) -> Unit, valueRange: ClosedFloatingPointRange<Float>) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(text = label)
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange
        )
    }
}

@Composable
fun SettingsSwitch(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun ThemeOption(theme: AppTheme, currentTheme: AppTheme, onSelect: (AppTheme) -> Unit) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        RadioButton(selected = theme == currentTheme, onClick = { onSelect(theme) })
        Text(text = theme.name)
    }
}
