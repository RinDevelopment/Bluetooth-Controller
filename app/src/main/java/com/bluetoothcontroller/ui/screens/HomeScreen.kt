package com.bluetoothcontroller.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bluetoothcontroller.ui.components.ConnectionStatusBar
import com.bluetoothcontroller.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToController: () -> Unit,
    onNavigateToKeyboard: () -> Unit,
    onNavigateToMouse: () -> Unit,
    onNavigateToPairing: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToCompatibility: () -> Unit
) {
    val connectionState by viewModel.connectionState.collectAsState()
    val connectedDeviceName by viewModel.connectedDeviceName.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        ConnectionStatusBar(
            connectionState = connectionState,
            deviceName = connectedDeviceName
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "BT Controller",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Turn your phone into a wireless controller, keyboard, and mouse.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                item {
                    HomeCard(
                        title = "Game Controller",
                        description = "Play games with virtual gamepad",
                        icon = Icons.Default.SportsEsports,
                        iconTint = Color(0xFF2196F3),
                        onClick = onNavigateToController
                    )
                }
                item {
                    HomeCard(
                        title = "Keyboard",
                        description = "Full QWERTY & Gaming layout",
                        icon = Icons.Default.Keyboard,
                        iconTint = Color(0xFF00BCD4),
                        onClick = onNavigateToKeyboard
                    )
                }
                item {
                    HomeCard(
                        title = "Mouse",
                        description = "Touchpad with gestures",
                        icon = Icons.Default.Mouse,
                        iconTint = Color(0xFF9C27B0),
                        onClick = onNavigateToMouse
                    )
                }
                item {
                    HomeCard(
                        title = "Connect Device",
                        description = "Pair via Bluetooth HID",
                        icon = Icons.Default.Bluetooth,
                        iconTint = Color(0xFF4CAF50),
                        onClick = onNavigateToPairing
                    )
                }
                item {
                    HomeCard(
                        title = "Settings",
                        description = "Sensitivity & Appearance",
                        icon = Icons.Default.Settings,
                        iconTint = Color(0xFFFF9800),
                        onClick = onNavigateToSettings
                    )
                }
                item {
                    HomeCard(
                        title = "Compatibility",
                        description = "Check HID & LE support",
                        icon = Icons.Default.Assessment,
                        iconTint = Color(0xFFE91E63),
                        onClick = onNavigateToCompatibility
                    )
                }
            }
            
            Text(
                text = "Privacy: Offline only. All input stays on your devices.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun HomeCard(
    title: String,
    description: String,
    icon: ImageVector,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconTint,
                modifier = Modifier
                    .size(44.dp)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
