package com.bluetoothcontroller.ui.screens

import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bluetoothcontroller.controller.DpadDirection
import com.bluetoothcontroller.ui.components.DPad
import com.bluetoothcontroller.ui.components.GameButton
import com.bluetoothcontroller.ui.components.Joystick
import com.bluetoothcontroller.ui.components.TriggerButton
import kotlinx.coroutines.delay

@Composable
fun ControllerScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit,
    // viewModel: ControllerViewModel would go here
) {
    val context = LocalContext.current
    
    // Request landscape orientation
    DisposableEffect(Unit) {
        val activity = context as? ComponentActivity
        val originalOrientation = activity?.requestedOrientation
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
        
        onDispose {
            if (originalOrientation != null) {
                activity.requestedOrientation = originalOrientation
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D1117))
            .systemBarsPadding()
    ) {
        // Top status
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color.Green, shape = androidx.compose.foundation.shape.CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Connected", color = Color.White, fontSize = 12.sp)
        }

        // Left Controls
        Column(
            modifier = Modifier
                .align(Alignment.CenterLeft)
                .padding(start = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TriggerButton(
                label = "L2",
                color = Color.White,
                width = 60.dp,
                height = 80.dp,
                isAnalog = true,
                onValueChange = { /* viewModel.updateL2(it) */ }
            )
            Spacer(modifier = Modifier.height(8.dp))
            GameButton(
                label = "L1",
                color = Color.White,
                size = 60.dp,
                onPress = { /* viewModel.pressButton(L1) */ },
                onRelease = { /* viewModel.releaseButton(L1) */ }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Joystick(
                size = 140.dp,
                onMove = { x, y -> /* viewModel.updateLeftStick(x, y) */ },
                onPress = { /* viewModel.pressButton(L3) */ },
                onRelease = { /* viewModel.releaseButton(L3) */ }
            )
            Spacer(modifier = Modifier.height(16.dp))
            DPad(
                size = 120.dp,
                onDirectionChange = { /* viewModel.updateDpad(it) */ }
            )
        }

        // Center Controls
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            GameButton(
                label = "SELECT",
                color = Color.Gray,
                size = 50.dp,
                onPress = { /* viewModel.pressButton(SELECT) */ },
                onRelease = { /* viewModel.releaseButton(SELECT) */ }
            )
            GameButton(
                label = "START",
                color = Color.Gray,
                size = 50.dp,
                onPress = { /* viewModel.pressButton(START) */ },
                onRelease = { /* viewModel.releaseButton(START) */ }
            )
        }

        // Right Controls
        Column(
            modifier = Modifier
                .align(Alignment.CenterRight)
                .padding(end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TriggerButton(
                label = "R2",
                color = Color.White,
                width = 60.dp,
                height = 80.dp,
                isAnalog = true,
                onValueChange = { /* viewModel.updateR2(it) */ }
            )
            Spacer(modifier = Modifier.height(8.dp))
            GameButton(
                label = "R1",
                color = Color.White,
                size = 60.dp,
                onPress = { /* viewModel.pressButton(R1) */ },
                onRelease = { /* viewModel.releaseButton(R1) */ }
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            // ABXY Diamond
            Box(modifier = Modifier.size(160.dp)) {
                GameButton(
                    label = "Y",
                    color = Color(0xFFFFC107),
                    size = 56.dp,
                    onPress = { /* viewModel.pressButton(Y) */ },
                    onRelease = { /* viewModel.releaseButton(Y) */ },
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                GameButton(
                    label = "X",
                    color = Color(0xFF2196F3),
                    size = 56.dp,
                    onPress = { /* viewModel.pressButton(X) */ },
                    onRelease = { /* viewModel.releaseButton(X) */ },
                    modifier = Modifier.align(Alignment.CenterLeft)
                )
                GameButton(
                    label = "B",
                    color = Color(0xFFF44336),
                    size = 56.dp,
                    onPress = { /* viewModel.pressButton(B) */ },
                    onRelease = { /* viewModel.releaseButton(B) */ },
                    modifier = Modifier.align(Alignment.CenterRight)
                )
                GameButton(
                    label = "A",
                    color = Color(0xFF4CAF50),
                    size = 56.dp,
                    onPress = { /* viewModel.pressButton(A) */ },
                    onRelease = { /* viewModel.releaseButton(A) */ },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Joystick(
                size = 120.dp,
                onMove = { x, y -> /* viewModel.updateRightStick(x, y) */ },
                onPress = { /* viewModel.pressButton(R3) */ },
                onRelease = { /* viewModel.releaseButton(R3) */ }
            )
        }

        // Bottom Bar
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            IconButton(onClick = onNavigateToSettings) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
            }
        }
    }
}
