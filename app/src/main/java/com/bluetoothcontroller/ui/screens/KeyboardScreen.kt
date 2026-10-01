package com.bluetoothcontroller.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bluetoothcontroller.ui.viewmodels.KeyboardViewModel

@Composable
fun KeyboardScreen(
    viewModel: KeyboardViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val isGamingMode by viewModel.isGamingMode.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D1117))
            .systemBarsPadding()
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onNavigateBack) { Text("Back") }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Gaming Mode", color = Color.White, modifier = Modifier.padding(end = 8.dp))
                Switch(checked = isGamingMode, onCheckedChange = { viewModel.toggleGamingMode() })
            }
            Button(onClick = onNavigateToSettings) { Text("Settings") }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Keyboard layout
        if (isGamingMode) {
            GamingKeyboardLayout(viewModel)
        } else {
            FullKeyboardLayout(viewModel)
        }
    }
}

@Composable
fun RowScope.KeyboardKey(
    label: String,
    modifier: Modifier = Modifier,
    isWide: Boolean = false,
    weight: Float = 1f,
    onPress: () -> Unit = {},
    onRelease: () -> Unit = {}
) {
    var isPressed by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current

    Box(
        modifier = modifier
            .padding(2.dp)
            .height(56.dp)
            .then(if (isWide) Modifier.fillMaxWidth() else Modifier.weight(weight))
            .background(
                color = if (isPressed) Color(0xFF2196F3) else Color(0xFF161B22),
                shape = RoundedCornerShape(4.dp)
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onPress()
                        tryAwaitRelease()
                        isPressed = false
                        onRelease()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = Color.White, fontSize = 16.sp)
    }
}

@Composable
fun FullKeyboardLayout(viewModel: KeyboardViewModel) {
    Column(modifier = Modifier.padding(8.dp)) {
        val row1 = listOf("Esc", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "Back")
        val row2 = listOf("Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "Del")
        val row3 = listOf("Caps", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Enter")
        val row4 = listOf("Shift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "Shift")

        Row(modifier = Modifier.fillMaxWidth()) {
            row1.forEach { key ->
                KeyboardKey(
                    label = key,
                    onPress = { viewModel.onKeyLabelPressed(key) },
                    onRelease = { viewModel.onKeyLabelReleased(key) }
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            row2.forEach { key ->
                KeyboardKey(
                    label = key,
                    onPress = { viewModel.onKeyLabelPressed(key) },
                    onRelease = { viewModel.onKeyLabelReleased(key) }
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            row3.forEach { key ->
                KeyboardKey(
                    label = key,
                    onPress = { viewModel.onKeyLabelPressed(key) },
                    onRelease = { viewModel.onKeyLabelReleased(key) }
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            row4.forEach { key ->
                KeyboardKey(
                    label = key,
                    onPress = { viewModel.onKeyLabelPressed(key) },
                    onRelease = { viewModel.onKeyLabelReleased(key) }
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            KeyboardKey("Ctrl", weight = 1.5f, onPress = { viewModel.onKeyLabelPressed("Ctrl") }, onRelease = { viewModel.onKeyLabelReleased("Ctrl") })
            KeyboardKey("Alt", weight = 1.5f, onPress = { viewModel.onKeyLabelPressed("Alt") }, onRelease = { viewModel.onKeyLabelReleased("Alt") })
            KeyboardKey("Space", weight = 5f, onPress = { viewModel.onKeyLabelPressed("Space") }, onRelease = { viewModel.onKeyLabelReleased("Space") })
            KeyboardKey("Alt", weight = 1.5f, onPress = { viewModel.onKeyLabelPressed("Alt") }, onRelease = { viewModel.onKeyLabelReleased("Alt") })
            KeyboardKey("Enter", weight = 1.5f, onPress = { viewModel.onKeyLabelPressed("Enter") }, onRelease = { viewModel.onKeyLabelReleased("Enter") })
        }
    }
}

@Composable
fun GamingKeyboardLayout(viewModel: KeyboardViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(3f)) {
            val row1 = listOf("Esc", "1", "2", "3", "4", "5")
            val row2 = listOf("Tab", "Q", "W", "E", "R", "T")
            val row3 = listOf("Shift", "A", "S", "D", "F", "G")
            val row4 = listOf("Ctrl", "Z", "X", "C", "V", "B")
            
            Row(modifier = Modifier.fillMaxWidth()) {
                row1.forEach { key ->
                    KeyboardKey(key, onPress = { viewModel.onKeyLabelPressed(key) }, onRelease = { viewModel.onKeyLabelReleased(key) })
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                row2.forEach { key ->
                    KeyboardKey(key, onPress = { viewModel.onKeyLabelPressed(key) }, onRelease = { viewModel.onKeyLabelReleased(key) })
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                row3.forEach { key ->
                    KeyboardKey(key, onPress = { viewModel.onKeyLabelPressed(key) }, onRelease = { viewModel.onKeyLabelReleased(key) })
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                row4.forEach { key ->
                    KeyboardKey(key, onPress = { viewModel.onKeyLabelPressed(key) }, onRelease = { viewModel.onKeyLabelReleased(key) })
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                KeyboardKey("Space", isWide = true, onPress = { viewModel.onKeyLabelPressed("Space") }, onRelease = { viewModel.onKeyLabelReleased("Space") })
            }
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // Arrows cluster
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(116.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                KeyboardKey("Up", weight = 1f, onPress = { viewModel.onKeyLabelPressed("Up") }, onRelease = { viewModel.onKeyLabelReleased("Up") })
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                KeyboardKey("Left", weight = 1f, onPress = { viewModel.onKeyLabelPressed("Left") }, onRelease = { viewModel.onKeyLabelReleased("Left") })
                KeyboardKey("Down", weight = 1f, onPress = { viewModel.onKeyLabelPressed("Down") }, onRelease = { viewModel.onKeyLabelReleased("Down") })
                KeyboardKey("Right", weight = 1f, onPress = { viewModel.onKeyLabelPressed("Right") }, onRelease = { viewModel.onKeyLabelReleased("Right") })
            }
        }
    }
}
