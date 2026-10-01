package com.bluetoothcontroller.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bluetoothcontroller.ui.components.Touchpad

@Composable
fun MouseScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    var sensitivity by remember { mutableStateOf(1.0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D1117))
            .systemBarsPadding()
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onNavigateBack) { Text("Back") }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Sensitivity", color = Color.White, modifier = Modifier.padding(end = 8.dp))
                Slider(
                    value = sensitivity,
                    onValueChange = { sensitivity = it },
                    valueRange = 0.1f..3.0f,
                    modifier = Modifier.width(120.dp)
                )
            }
            Button(onClick = onNavigateToSettings) { Text("Settings") }
        }

        // Touchpad Area
        Touchpad(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            sensitivity = sensitivity,
            onMove = { dx, dy -> /* viewModel.moveMouse(dx, dy) */ },
            onTap = { /* viewModel.clickLeft() */ },
            onDoubleTap = { /* viewModel.doubleClick() */ },
            onTwoFingerTap = { /* viewModel.clickRight() */ },
            onScroll = { dy -> /* viewModel.scroll(dy) */ }
        )

        // Mouse Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(64.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { /* viewModel.clickLeft() */ },
                modifier = Modifier.weight(2f).fillMaxHeight(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF161B22))
            ) { Text("LEFT") }
            
            Button(
                onClick = { /* viewModel.clickMiddle() */ },
                modifier = Modifier.weight(1f).fillMaxHeight(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF161B22))
            ) { Text("MIDDLE") }
            
            Button(
                onClick = { /* viewModel.clickRight() */ },
                modifier = Modifier.weight(2f).fillMaxHeight(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF161B22))
            ) { Text("RIGHT") }
        }
    }
}
