package com.bluetoothcontroller.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun Joystick(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    baseColor: Color = Color(0xFF161B22),
    thumbColor: Color = Color(0xFF2196F3),
    opacity: Float = 0.8f,
    onMove: (Float, Float) -> Unit,
    onPress: () -> Unit = {},
    onRelease: () -> Unit = {}
) {
    var thumbPosition by remember { mutableStateOf(Offset.Zero) }
    var isDragging by remember { mutableStateOf(false) }
    
    val animatedX by animateFloatAsState(
        targetValue = thumbPosition.x,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "thumbX"
    )
    val animatedY by animateFloatAsState(
        targetValue = thumbPosition.y,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "thumbY"
    )

    val currentX = if (isDragging) thumbPosition.x else animatedX
    val currentY = if (isDragging) thumbPosition.y else animatedY

    Box(
        modifier = modifier
            .size(size)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        isDragging = true
                        onPress()
                        // Calculate offset relative to center
                        val center = Offset(this.size.width / 2f, this.size.height / 2f)
                        val radius = this.size.width / 2f
                        var dx = offset.x - center.x
                        var dy = offset.y - center.y
                        
                        val distance = sqrt(dx * dx + dy * dy)
                        if (distance > radius) {
                            val angle = atan2(dy, dx)
                            dx = cos(angle) * radius
                            dy = sin(angle) * radius
                        }
                        thumbPosition = Offset(dx, dy)
                        onMove(dx / radius, dy / radius)
                    },
                    onDragEnd = {
                        isDragging = false
                        thumbPosition = Offset.Zero
                        onMove(0f, 0f)
                        onRelease()
                    },
                    onDragCancel = {
                        isDragging = false
                        thumbPosition = Offset.Zero
                        onMove(0f, 0f)
                        onRelease()
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val center = Offset(this.size.width / 2f, this.size.height / 2f)
                        val radius = this.size.width / 2f
                        
                        var newX = thumbPosition.x + dragAmount.x
                        var newY = thumbPosition.y + dragAmount.y
                        
                        val distance = sqrt(newX * newX + newY * newY)
                        if (distance > radius) {
                            val angle = atan2(newY, newX)
                            newX = cos(angle) * radius
                            newY = sin(angle) * radius
                        }
                        thumbPosition = Offset(newX, newY)
                        onMove(newX / radius, newY / radius)
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val center = this.center
            val radius = this.size.width / 2f
            val thumbRadius = radius * 0.4f

            // Base
            drawCircle(
                color = baseColor.copy(alpha = opacity),
                radius = radius,
                center = center
            )

            // Grid lines
            drawLine(
                color = Color.White.copy(alpha = 0.1f),
                start = Offset(center.x, center.y - radius),
                end = Offset(center.x, center.y + radius),
                strokeWidth = 2f
            )
            drawLine(
                color = Color.White.copy(alpha = 0.1f),
                start = Offset(center.x - radius, center.y),
                end = Offset(center.x + radius, center.y),
                strokeWidth = 2f
            )

            // Outer ring
            drawCircle(
                color = if (isDragging) thumbColor.copy(alpha = opacity) else Color.White.copy(alpha = 0.2f),
                radius = radius,
                center = center,
                style = Stroke(width = 4f)
            )
            
            // Dead zone
            drawCircle(
                color = Color.White.copy(alpha = 0.05f),
                radius = radius * 0.2f,
                center = center
            )

            // Thumb
            drawCircle(
                color = thumbColor.copy(alpha = opacity),
                radius = thumbRadius,
                center = Offset(center.x + currentX, center.y + currentY)
            )
        }
    }
}
