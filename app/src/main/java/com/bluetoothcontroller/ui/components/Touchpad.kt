package com.bluetoothcontroller.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun Touchpad(
    modifier: Modifier = Modifier,
    sensitivity: Float = 1.0f,
    onMove: (Float, Float) -> Unit,
    onTap: () -> Unit,
    onDoubleTap: () -> Unit,
    onTwoFingerTap: () -> Unit,
    onScroll: (Float) -> Unit
) {
    val activePointers = remember { mutableStateListOf<Offset>() }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF161B22))
            .pointerInput(Unit) {
                awaitEachGesture {
                    val down = awaitFirstDown()
                    activePointers.clear()
                    activePointers.add(down.position)
                    
                    var pointerCount = 1
                    var isScrolling = false
                    var totalPan = Offset.Zero
                    
                    do {
                        val event: PointerEvent = awaitPointerEvent(pass = PointerEventPass.Main)
                        
                        // Update visualization points
                        activePointers.clear()
                        event.changes.forEach { if (it.pressed) activePointers.add(it.position) }
                        
                        val newPointerCount = event.changes.count { it.pressed }
                        if (newPointerCount > pointerCount) {
                            pointerCount = newPointerCount
                        }
                        
                        when (event.type) {
                            PointerEventType.Move -> {
                                if (newPointerCount == 1) {
                                    val pan = event.calculatePan()
                                    if (pan != Offset.Zero) {
                                        onMove(pan.x * sensitivity, pan.y * sensitivity)
                                        event.changes.forEach { it.consume() }
                                    }
                                } else if (newPointerCount == 2) {
                                    isScrolling = true
                                    val pan = event.calculatePan()
                                    if (pan.y != 0f) {
                                        onScroll(pan.y * sensitivity * 0.1f)
                                        event.changes.forEach { it.consume() }
                                    }
                                }
                            }
                            PointerEventType.Release -> {
                                // Tap detection logic is somewhat simplified here for illustration
                                if (!isScrolling && event.changes.none { it.pressed }) {
                                    val duration = event.changes.first().uptimeMillis - down.uptimeMillis
                                    if (duration < 200) { // Tap
                                        if (pointerCount == 1) {
                                            // Handle double tap properly requires more complex state tracking
                                            // We'll emit tap for now
                                            onTap()
                                        } else if (pointerCount == 2) {
                                            onTwoFingerTap()
                                        }
                                    }
                                }
                            }
                        }
                    } while (event.changes.any { it.pressed })
                    
                    activePointers.clear()
                }
            }
    ) {
        // Draw grid and touch points
        Canvas(modifier = Modifier.fillMaxSize()) {
            val spacing = 30.dp.toPx()
            val dotRadius = 1.dp.toPx()
            
            // Dot grid
            for (x in 0..size.width.toInt() step spacing.toInt()) {
                for (y in 0..size.height.toInt() step spacing.toInt()) {
                    drawCircle(
                        color = Color.White.copy(alpha = 0.05f),
                        radius = dotRadius,
                        center = Offset(x.toFloat(), y.toFloat())
                    )
                }
            }
            
            // Touch points
            activePointers.forEach { offset ->
                drawCircle(
                    color = Color(0xFF2196F3).copy(alpha = 0.4f),
                    radius = 24.dp.toPx(),
                    center = offset
                )
            }
        }
    }
}
