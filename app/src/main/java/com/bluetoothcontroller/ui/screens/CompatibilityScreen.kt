package com.bluetoothcontroller.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bluetoothcontroller.data.CompatLevel
import com.bluetoothcontroller.data.CompatibilityResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompatibilityScreen(
    compatibilityResult: CompatibilityResult,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Device Compatibility") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when (compatibilityResult.overall) {
                            CompatLevel.SUPPORTED -> MaterialTheme.colorScheme.primaryContainer
                            CompatLevel.LIMITED -> MaterialTheme.colorScheme.tertiaryContainer
                            CompatLevel.UNSUPPORTED -> MaterialTheme.colorScheme.errorContainer
                        }
                    )
                ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Overall Status: ${compatibilityResult.overall}",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = when (compatibilityResult.overall) {
                                    CompatLevel.SUPPORTED -> "Your device fully supports Bluetooth HID controller features."
                                    CompatLevel.LIMITED -> "Your device has partial Bluetooth HID support. Fallback RFCOMM mode may be used."
                                    CompatLevel.UNSUPPORTED -> "Bluetooth HID Device mode is not supported on this device hardware/OS."
                                },
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
            }

            item {
                Text(
                    text = "Detailed Checks",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item { CompatCheckItem("Android Version (API 28+)", compatibilityResult.androidVersion) }
            item { CompatCheckItem("Bluetooth Hardware", compatibilityResult.bluetooth) }
            item { CompatCheckItem("Bluetooth Low Energy", compatibilityResult.bluetoothLE) }
            item { CompatCheckItem("Bluetooth Classic", compatibilityResult.bluetoothClassic) }
            item { CompatCheckItem("Bluetooth HID Device API", compatibilityResult.hidDevice) }
            item { CompatCheckItem("Permissions Granted", compatibilityResult.permissions) }

            if (compatibilityResult.details.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Notes & Diagnostics",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(compatibilityResult.details.size) { index ->
                    Text(
                        text = "• ${compatibilityResult.details[index]}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CompatCheckItem(name: String, level: CompatLevel) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name, style = MaterialTheme.typography.bodyMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            val (icon, color, text) = when (level) {
                CompatLevel.SUPPORTED -> Triple(Icons.Default.CheckCircle, Color(0xFF4CAF50), "Supported")
                CompatLevel.LIMITED -> Triple(Icons.Default.Warning, Color(0xFFFF9800), "Limited")
                CompatLevel.UNSUPPORTED -> Triple(Icons.Default.Cancel, Color(0xFFF44336), "Unsupported")
            }
            Icon(imageVector = icon, contentDescription = text, tint = color, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text, color = color, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
        }
    }
}
