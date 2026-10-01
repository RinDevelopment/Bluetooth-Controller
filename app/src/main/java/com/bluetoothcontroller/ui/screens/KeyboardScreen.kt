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

@Composable
fun KeyboardScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    var isGamingMode by remember { mutableStateOf(false) }

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
                Switch(checked = isGamingMode, onCheckedChange = { isGamingMode = it })
            }
            Button(onClick = onNavigateToSettings) { Text("Settings") }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Keyboard layout
        if (isGamingMode) {
            GamingKeyboardLayout()
        } else {
            FullKeyboardLayout()
        }
    }
}

@Composable
fun KeyboardKey(
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
            .let { if (isWide) it.fillMaxWidth() else it.weight(weight) }
            .background(
                color = if (isPressed) Color(0xFF2196F3) else Color(0xFF161B22),
                shape = RoundedCornerShape(4.dp)
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardPress)
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
fun FullKeyboardLayout() {
    Column(modifier = Modifier.padding(8.dp)) {
        val row1 = listOf("Esc", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "Back")
        val row2 = listOf("Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "Del")
        val row3 = listOf("Caps", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Enter")
        val row4 = listOf("Shift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "Shift")

        Row(modifier = Modifier.fillMaxWidth()) { row1.forEach { KeyboardKey(it) } }
        Row(modifier = Modifier.fillMaxWidth()) { row2.forEach { KeyboardKey(it) } }
        Row(modifier = Modifier.fillMaxWidth()) { row3.forEach { KeyboardKey(it) } }
        Row(modifier = Modifier.fillMaxWidth()) { row4.forEach { KeyboardKey(it) } }
        Row(modifier = Modifier.fillMaxWidth()) {
            KeyboardKey("Ctrl", weight = 1.5f)
            KeyboardKey("Alt", weight = 1.5f)
            KeyboardKey("Space", weight = 5f)
            KeyboardKey("Alt", weight = 1.5f)
            KeyboardKey("Fn", weight = 1.5f)
        }
    }
}

@Composable
fun GamingKeyboardLayout() {
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
            
            Row(modifier = Modifier.fillMaxWidth()) { row1.forEach { KeyboardKey(it) } }
            Row(modifier = Modifier.fillMaxWidth()) { row2.forEach { KeyboardKey(it) } }
            Row(modifier = Modifier.fillMaxWidth()) { row3.forEach { KeyboardKey(it) } }
            Row(modifier = Modifier.fillMaxWidth()) { row4.forEach { KeyboardKey(it) } }
            Row(modifier = Modifier.fillMaxWidth()) { KeyboardKey("Space", isWide = true) }
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // Arrows cluster
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(116.dp)) // Offset to align with bottom rows
            KeyboardKey("Up")
            Row(modifier = Modifier.fillMaxWidth()) {
                KeyboardKey("Left", weight = 1f)
                KeyboardKey("Down", weight = 1f)
                KeyboardKey("Right", weight = 1f)
            }
        }
    }
}
