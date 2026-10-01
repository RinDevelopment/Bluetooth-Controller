package com.bluetoothcontroller.ui.components

import android.view.HapticFeedbackConstants
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bluetoothcontroller.controller.DpadDirection
import kotlin.math.atan2

@Composable
fun DPad(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    color: Color = Color(0xFF2196F3),
    opacity: Float = 0.8f,
    onDirectionChange: (DpadDirection) -> Unit
) {
    var currentDirection by remember { mutableStateOf(DpadDirection.NONE) }
    val haptic = LocalHapticFeedback.current

    fun updateDirection(newDirection: DpadDirection) {
        if (currentDirection != newDirection) {
            currentDirection = newDirection
            onDirectionChange(newDirection)
            if (newDirection != DpadDirection.NONE) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            }
        }
    }

    Box(
        modifier = modifier
            .size(size)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val center = Offset(this.size.width / 2f, this.size.height / 2f)
                        val dx = offset.x - center.x
                        val dy = offset.y - center.y
                        val radius = this.size.width / 2f
                        if (dx * dx + dy * dy <= radius * radius) {
                            val angle = (Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())) + 360) % 360
                            updateDirection(getDirectionFromAngle(angle))
                        }
                    },
                    onDragEnd = { updateDirection(DpadDirection.NONE) },
                    onDragCancel = { updateDirection(DpadDirection.NONE) },
                    onDrag = { change, _ ->
                        val offset = change.position
                        val center = Offset(this.size.width / 2f, this.size.height / 2f)
                        val dx = offset.x - center.x
                        val dy = offset.y - center.y
                        val radius = this.size.width / 2f
                        
                        if (dx * dx + dy * dy <= radius * radius) {
                            val angle = (Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())) + 360) % 360
                            updateDirection(getDirectionFromAngle(angle))
                        } else {
                            updateDirection(DpadDirection.NONE)
                        }
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val w = this.size.width
            val h = this.size.height
            val cX = w / 2f
            val cY = h / 2f
            val thickness = w * 0.35f
            val halfT = thickness / 2f
            
            val baseColor = Color(0xFF161B22).copy(alpha = opacity)
            val highlightColor = color.copy(alpha = opacity)
            
            // Draw cross
            val path = Path().apply {
                moveTo(cX - halfT, 0f)
                lineTo(cX + halfT, 0f)
                lineTo(cX + halfT, cY - halfT)
                lineTo(w, cY - halfT)
                lineTo(w, cY + halfT)
                lineTo(cX + halfT, cY + halfT)
                lineTo(cX + halfT, h)
                lineTo(cX - halfT, h)
                lineTo(cX - halfT, cY + halfT)
                lineTo(0f, cY + halfT)
                lineTo(0f, cY - halfT)
                lineTo(cX - halfT, cY - halfT)
                close()
            }
            
            drawPath(path = path, color = baseColor)

            // Draw highlights based on direction
            val isUp = currentDirection in listOf(DpadDirection.UP, DpadDirection.UP_LEFT, DpadDirection.UP_RIGHT)
            val isDown = currentDirection in listOf(DpadDirection.DOWN, DpadDirection.DOWN_LEFT, DpadDirection.DOWN_RIGHT)
            val isLeft = currentDirection in listOf(DpadDirection.LEFT, DpadDirection.UP_LEFT, DpadDirection.DOWN_LEFT)
            val isRight = currentDirection in listOf(DpadDirection.RIGHT, DpadDirection.UP_RIGHT, DpadDirection.DOWN_RIGHT)

            if (isUp) {
                drawRect(color = highlightColor, topLeft = Offset(cX - halfT, 0f), size = Size(thickness, cY))
            }
            if (isDown) {
                drawRect(color = highlightColor, topLeft = Offset(cX - halfT, cY), size = Size(thickness, cY))
            }
            if (isLeft) {
                drawRect(color = highlightColor, topLeft = Offset(0f, cY - halfT), size = Size(cX, thickness))
            }
            if (isRight) {
                drawRect(color = highlightColor, topLeft = Offset(cX, cY - halfT), size = Size(cX, thickness))
            }
        }
    }
}

private fun getDirectionFromAngle(angle: Double): DpadDirection {
    // Note: in canvas Y is down, so angles are: 0=Right, 90=Down, 180=Left, 270=Up
    return when {
        angle >= 337.5 || angle < 22.5 -> DpadDirection.RIGHT
        angle >= 22.5 && angle < 67.5 -> DpadDirection.DOWN_RIGHT
        angle >= 67.5 && angle < 112.5 -> DpadDirection.DOWN
        angle >= 112.5 && angle < 157.5 -> DpadDirection.DOWN_LEFT
        angle >= 157.5 && angle < 202.5 -> DpadDirection.LEFT
        angle >= 202.5 && angle < 247.5 -> DpadDirection.UP_LEFT
        angle >= 247.5 && angle < 292.5 -> DpadDirection.UP
        angle >= 292.5 && angle < 337.5 -> DpadDirection.UP_RIGHT
        else -> DpadDirection.NONE
    }
}
