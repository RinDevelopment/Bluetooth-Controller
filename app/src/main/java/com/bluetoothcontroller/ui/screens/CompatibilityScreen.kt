package com.bluetoothcontroller.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                title = { Text("Device Compatibility") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Overall Status", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                        Text(
                            text = compatibilityResult.overall.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            item {
                Text("Checks", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            }

            item {
                CompatCheckItem("Android Version", compatibilityResult.androidVersion)
                CompatCheckItem("Bluetooth", compatibilityResult.bluetooth)
                CompatCheckItem("Bluetooth LE", compatibilityResult.bluetoothLE)
                CompatCheckItem("Bluetooth Classic", compatibilityResult.bluetoothClassic)
                CompatCheckItem("HID Device API", compatibilityResult.hidDevice)
                CompatCheckItem("Permissions", compatibilityResult.permissions)
            }

            if (compatibilityResult.details.isNotEmpty()) {
                item {
                    Text("Details", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), modifier = Modifier.padding(top = 8.dp))
                    compatibilityResult.details.forEach { detail ->
                        Text("• $detail", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }

            if (compatibilityResult.permissions != CompatLevel.SUPPORTED) {
                item {
                    Button(
                        onClick = { /* Grant permissions */ },
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    ) {
                        Text("Grant Permissions")
                    }
                }
            }
        }
    }
}

@Composable
fun CompatCheckItem(name: String, level: CompatLevel) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name)
        Text(
            text = when (level) {
                CompatLevel.SUPPORTED -> "✅ Supported"
                CompatLevel.LIMITED -> "⚠️ Limited"
                CompatLevel.UNSUPPORTED -> "❌ Unsupported"
            }
        )
    }
}
