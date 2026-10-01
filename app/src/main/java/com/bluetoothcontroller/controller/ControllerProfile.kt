package com.bluetoothcontroller.controller

/**
 * Defines a complete configuration profile for a controller layout.
 */
data class ControllerProfile(
    val id: String,
    val name: String,
    val icon: String,
    val buttonLayouts: Map<String, ButtonLayoutConfig>,
    val joystickConfig: JoystickConfig,
    val triggerConfig: TriggerConfig,
    val isBuiltIn: Boolean = false
) {
    companion object {
        val DEFAULT_PROFILES: List<ControllerProfile> by lazy {
            listOf(
                createDefaultProfile(),
                createFpsProfile(),
                createRacingProfile(),
                createEmulatorProfile(),
                createMinecraftProfile(),
                createCustomProfile()
            )
        }

        private fun createDefaultProfile() = ControllerProfile(
            id = "default",
            name = "Default",
            icon = "ic_controller",
            buttonLayouts = mapOf(
                "A" to ButtonLayoutConfig(0.85f, 0.75f, 1f, 1f, "A", true, GameButton.A),
                "B" to ButtonLayoutConfig(0.95f, 0.65f, 1f, 1f, "B", true, GameButton.B),
                "X" to ButtonLayoutConfig(0.75f, 0.65f, 1f, 1f, "X", true, GameButton.X),
                "Y" to ButtonLayoutConfig(0.85f, 0.55f, 1f, 1f, "Y", true, GameButton.Y)
            ),
            joystickConfig = JoystickConfig(0.1f, 1.0f, false, false, 1.0f, ResponseCurve.LINEAR),
            triggerConfig = TriggerConfig(true, 1.0f),
            isBuiltIn = true
        )

        private fun createFpsProfile() = ControllerProfile(
            id = "fps",
            name = "FPS",
            icon = "ic_crosshair",
            buttonLayouts = mapOf(
                "A" to ButtonLayoutConfig(0.85f, 0.8f, 1f, 1f, "Jump", true, GameButton.A),
                "R1" to ButtonLayoutConfig(0.9f, 0.2f, 1.2f, 1f, "Fire", true, GameButton.R1)
            ),
            joystickConfig = JoystickConfig(0.05f, 1.5f, false, false, 1.0f, ResponseCurve.S_CURVE),
            triggerConfig = TriggerConfig(false, 1.5f), // Digital triggers for faster shooting
            isBuiltIn = true
        )

        private fun createRacingProfile() = ControllerProfile(
            id = "racing",
            name = "Racing",
            icon = "ic_car",
            buttonLayouts = mapOf(
                "A" to ButtonLayoutConfig(0.85f, 0.75f, 1f, 1f, "Handbrake", true, GameButton.A)
            ),
            joystickConfig = JoystickConfig(0.1f, 1.0f, false, false, 1.2f, ResponseCurve.LINEAR),
            triggerConfig = TriggerConfig(true, 1.0f), // Analog highly emphasized
            isBuiltIn = true
        )

        private fun createEmulatorProfile() = ControllerProfile(
            id = "emulator",
            name = "Emulator",
            icon = "ic_gameboy",
            buttonLayouts = mapOf(
                "A" to ButtonLayoutConfig(0.9f, 0.7f, 1f, 1f, "A", true, GameButton.A),
                "B" to ButtonLayoutConfig(0.8f, 0.8f, 1f, 1f, "B", true, GameButton.B)
            ),
            joystickConfig = JoystickConfig(0.2f, 1.0f, false, false, 1.0f, ResponseCurve.LINEAR),
            triggerConfig = TriggerConfig(false, 1.0f),
            isBuiltIn = true
        )

        private fun createMinecraftProfile() = ControllerProfile(
            id = "minecraft",
            name = "Minecraft",
            icon = "ic_block",
            buttonLayouts = mapOf(
                "A" to ButtonLayoutConfig(0.85f, 0.75f, 1f, 1f, "Jump", true, GameButton.A),
                "B" to ButtonLayoutConfig(0.95f, 0.65f, 1f, 1f, "Sneak", true, GameButton.B)
            ),
            joystickConfig = JoystickConfig(0.1f, 1.0f, false, false, 1.0f, ResponseCurve.LINEAR),
            triggerConfig = TriggerConfig(true, 1.0f),
            isBuiltIn = true
        )

        private fun createCustomProfile() = ControllerProfile(
            id = "custom_empty",
            name = "Custom",
            icon = "ic_edit",
            buttonLayouts = emptyMap(),
            joystickConfig = JoystickConfig(0.1f, 1.0f, false, false, 1.0f, ResponseCurve.LINEAR),
            triggerConfig = TriggerConfig(true, 1.0f),
            isBuiltIn = false
        )
    }
}

data class ButtonLayoutConfig(
    val x: Float, // Relative 0-1
    val y: Float, // Relative 0-1
    val size: Float,
    val opacity: Float,
    val label: String,
    val visible: Boolean,
    val action: GameButton?
)

data class JoystickConfig(
    val deadZone: Float,
    val sensitivity: Float,
    val invertX: Boolean,
    val invertY: Boolean,
    val size: Float,
    val responseCurve: ResponseCurve
)

data class TriggerConfig(
    val isAnalog: Boolean,
    val sensitivity: Float
)

enum class ResponseCurve {
    LINEAR,
    SQUARED,
    CUBED,
    S_CURVE
}
