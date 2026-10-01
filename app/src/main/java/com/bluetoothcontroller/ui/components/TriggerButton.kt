package com.bluetoothcontroller.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TriggerButton(
    label: String,
    color: Color,
    width: Dp = 60.dp,
    height: Dp = 100.dp,
    isAnalog: Boolean = true,
    opacity: Float = 0.8f,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var pressure by remember { mutableStateOf(0f) }
    val haptic = LocalHapticFeedback.current

    val animatedPressure by animateFloatAsState(
        targetValue = pressure,
        animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessHigh),
        label = "pressure"
    )

    val inputModifier = if (isAnalog) {
        Modifier.pointerInput(Unit) {
            detectDragGestures(
                onDragStart = { offset ->
                    val newPressure = 1f - (offset.y / size.height).coerceIn(0f, 1f)
                    pressure = newPressure
                    onValueChange(newPressure)
                    if (newPressure > 0.05f) haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                },
                onDragEnd = {
                    pressure = 0f
                    onValueChange(0f)
                },
                onDragCancel = {
                    pressure = 0f
                    onValueChange(0f)
                },
                onDrag = { change, _ ->
                    val newPressure = 1f - (change.position.y / size.height).coerceIn(0f, 1f)
                    val oldThreshold = (pressure * 10).toInt()
                    val newThreshold = (newPressure * 10).toInt()
                    
                    pressure = newPressure
                    onValueChange(newPressure)
                    
                    if (oldThreshold != newThreshold) {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    }
                }
            )
        }
    } else {
        Modifier.pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    pressure = 1f
                    onValueChange(1f)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    tryAwaitRelease()
                    pressure = 0f
                    onValueChange(0f)
                }
            )
        }
    }

    Box(
        modifier = modifier
            .size(width, height)
            .then(inputModifier),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val cornerRadius = CornerRadius(16.dp.toPx())
            
            // Background
            drawRoundRect(
                color = Color(0xFF161B22).copy(alpha = opacity),
                size = size,
                cornerRadius = cornerRadius
            )
            
            // Fill
            val fillHeight = size.height * animatedPressure
            drawRoundRect(
                color = color.copy(alpha = opacity),
                topLeft = Offset(0f, size.height - fillHeight),
                size = Size(size.width, fillHeight),
                cornerRadius = cornerRadius
            )
            
            // Border
            drawRoundRect(
                color = Color.White.copy(alpha = 0.2f),
                size = size,
                cornerRadius = cornerRadius,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
            )
        }
        
        Text(
            text = label,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}
