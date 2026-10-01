package com.bluetoothcontroller.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.bluetoothcontroller.ui.components.GameButton
import kotlin.math.roundToInt

@Composable
fun LayoutEditorScreen(
    onNavigateBack: () -> Unit
) {
    var gridSnapEnabled by remember { mutableStateOf(false) }
    
    // Sample draggable item
    var buttonOffset by remember { mutableStateOf(Offset(200f, 200f)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D1117))
            .systemBarsPadding()
    ) {
        // Workspace
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(buttonOffset.x.roundToInt(), buttonOffset.y.roundToInt()) }
                    .pointerInput(gridSnapEnabled) {
                        detectDragGesturesAfterLongPress { change, dragAmount ->
                            change.consume()
                            var newX = buttonOffset.x + dragAmount.x
                            var newY = buttonOffset.y + dragAmount.y
                            
                            if (gridSnapEnabled) {
                                val gridSize = 20f
                                newX = (newX / gridSize).roundToInt() * gridSize
                                newY = (newY / gridSize).roundToInt() * gridSize
                            }
                            
                            buttonOffset = Offset(newX, newY)
                        }
                    }
            ) {
                GameButton(
                    label = "A",
                    color = Color(0xFF4CAF50),
                    onPress = {},
                    onRelease = {}
                )
            }
        }

        // Top Bar
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .background(Color(0xFF161B22))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onNavigateBack) { Text("Exit") }
            Text("Layout Editor", color = Color.White)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Snap", color = Color.White, modifier = Modifier.padding(end = 8.dp))
                Switch(checked = gridSnapEnabled, onCheckedChange = { gridSnapEnabled = it })
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { /* Save */ }) { Text("Save") }
            }
        }

        // Bottom Toolbar
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color(0xFF161B22))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { /* Add Button */ }) { Text("+ Button") }
            Button(onClick = { /* Add DPad */ }) { Text("+ DPad") }
            Button(onClick = { /* Add Joystick */ }) { Text("+ Joystick") }
            Button(
                onClick = { /* Delete Selected */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) { Text("Delete") }
        }
    }
}
