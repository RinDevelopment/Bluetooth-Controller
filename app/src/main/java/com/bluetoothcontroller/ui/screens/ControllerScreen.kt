package com.bluetoothcontroller.ui.screens

import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bluetoothcontroller.bluetooth.ConnectionState
import com.bluetoothcontroller.controller.DpadDirection
import com.bluetoothcontroller.controller.GameButton
import com.bluetoothcontroller.ui.components.DPad
import com.bluetoothcontroller.ui.components.GameButton as ControllerGameButton
import com.bluetoothcontroller.ui.components.Joystick
import com.bluetoothcontroller.ui.components.TriggerButton
import com.bluetoothcontroller.ui.viewmodels.ControllerViewModel

@Composable
fun ControllerScreen(
    viewModel: ControllerViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val context = LocalContext.current
    val connectionState by viewModel.connectionState.collectAsState()
    
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
            val statusColor = when (connectionState) {
                ConnectionState.CONNECTED -> Color(0xFF4CAF50)
                ConnectionState.CONNECTING, ConnectionState.RECONNECTING -> Color(0xFFFF9800)
                else -> Color(0xFFF44336)
            }
            val statusText = when (connectionState) {
                ConnectionState.CONNECTED -> "Connected"
                ConnectionState.CONNECTING -> "Connecting..."
                ConnectionState.RECONNECTING -> "Reconnecting..."
                else -> "Disconnected"
            }

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(statusColor, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(statusText, color = Color.White, fontSize = 12.sp)
        }

        // Left Controls
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TriggerButton(
                label = "L2",
                color = Color.White,
                width = 56.dp,
                height = 70.dp,
                isAnalog = true,
                onValueChange = { viewModel.onTriggerChange(true, it) }
            )
            Spacer(modifier = Modifier.height(6.dp))
            ControllerGameButton(
                label = "L1",
                color = Color.White,
                size = 56.dp,
                onPress = { viewModel.onButtonPress(GameButton.L1) },
                onRelease = { viewModel.onButtonRelease(GameButton.L1) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Joystick(
                size = 130.dp,
                onMove = { x, y -> viewModel.onJoystickMove(true, x, y) },
                onPress = { viewModel.onButtonPress(GameButton.LEFT_STICK_PRESS) },
                onRelease = { viewModel.onButtonRelease(GameButton.LEFT_STICK_PRESS) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            DPad(
                size = 110.dp,
                onDirectionChange = { viewModel.onDpadPress(it) }
            )
        }

        // Center Controls
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ControllerGameButton(
                label = "SELECT",
                color = Color.Gray,
                size = 46.dp,
                onPress = { viewModel.onButtonPress(GameButton.SELECT) },
                onRelease = { viewModel.onButtonRelease(GameButton.SELECT) }
            )
            ControllerGameButton(
                label = "START",
                color = Color.Gray,
                size = 46.dp,
                onPress = { viewModel.onButtonPress(GameButton.START) },
                onRelease = { viewModel.onButtonRelease(GameButton.START) }
            )
        }

        // Right Controls
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TriggerButton(
                label = "R2",
                color = Color.White,
                width = 56.dp,
                height = 70.dp,
                isAnalog = true,
                onValueChange = { viewModel.onTriggerChange(false, it) }
            )
            Spacer(modifier = Modifier.height(6.dp))
            ControllerGameButton(
                label = "R1",
                color = Color.White,
                size = 56.dp,
                onPress = { viewModel.onButtonPress(GameButton.R1) },
                onRelease = { viewModel.onButtonRelease(GameButton.R1) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            
            // ABXY Diamond
            Box(modifier = Modifier.size(150.dp)) {
                ControllerGameButton(
                    label = "Y",
                    color = Color(0xFFFFC107),
                    size = 52.dp,
                    onPress = { viewModel.onButtonPress(GameButton.Y) },
                    onRelease = { viewModel.onButtonRelease(GameButton.Y) },
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                ControllerGameButton(
                    label = "X",
                    color = Color(0xFF2196F3),
                    size = 52.dp,
                    onPress = { viewModel.onButtonPress(GameButton.X) },
                    onRelease = { viewModel.onButtonRelease(GameButton.X) },
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                ControllerGameButton(
                    label = "B",
                    color = Color(0xFFF44336),
                    size = 52.dp,
                    onPress = { viewModel.onButtonPress(GameButton.B) },
                    onRelease = { viewModel.onButtonRelease(GameButton.B) },
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
                ControllerGameButton(
                    label = "A",
                    color = Color(0xFF4CAF50),
                    size = 52.dp,
                    onPress = { viewModel.onButtonPress(GameButton.A) },
                    onRelease = { viewModel.onButtonRelease(GameButton.A) },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
            
            Spacer(modifier = Modifier.height(10.dp))
            Joystick(
                size = 110.dp,
                onMove = { x, y -> viewModel.onJoystickMove(false, x, y) },
                onPress = { viewModel.onButtonPress(GameButton.RIGHT_STICK_PRESS) },
                onRelease = { viewModel.onButtonRelease(GameButton.RIGHT_STICK_PRESS) }
            )
        }

        // Top navigation icons
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        }
        
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            IconButton(onClick = onNavigateToSettings) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
            }
        }
    }
}
