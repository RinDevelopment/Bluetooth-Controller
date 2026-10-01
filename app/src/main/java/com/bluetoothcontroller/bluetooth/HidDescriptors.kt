package com.bluetoothcontroller.bluetooth

/**
 * Object containing HID Report Descriptors.
 */
object HidDescriptors {
    /**
     * Standard HID keyboard (Report ID 1)
     */
    val KEYBOARD_DESCRIPTOR = byteArrayOf(
        0x05.toByte(), 0x01.toByte(), // Usage Page (Generic Desktop)
        0x09.toByte(), 0x06.toByte(), // Usage (Keyboard)
        0xA1.toByte(), 0x01.toByte(), // Collection (Application)
        0x85.toByte(), 0x01.toByte(), //   Report ID (1)
        0x05.toByte(), 0x07.toByte(), //   Usage Page (Key Codes)
        0x19.toByte(), 0xE0.toByte(), //   Usage Minimum (224)
        0x29.toByte(), 0xE7.toByte(), //   Usage Maximum (231)
        0x15.toByte(), 0x00.toByte(), //   Logical Minimum (0)
        0x25.toByte(), 0x01.toByte(), //   Logical Maximum (1)
        0x75.toByte(), 0x01.toByte(), //   Report Size (1)
        0x95.toByte(), 0x08.toByte(), //   Report Count (8)
        0x81.toByte(), 0x02.toByte(), //   Input (Data, Variable, Absolute) ; Modifier byte
        0x95.toByte(), 0x01.toByte(), //   Report Count (1)
        0x75.toByte(), 0x08.toByte(), //   Report Size (8)
        0x81.toByte(), 0x01.toByte(), //   Input (Constant) ; Reserved byte
        0x95.toByte(), 0x06.toByte(), //   Report Count (6)
        0x75.toByte(), 0x08.toByte(), //   Report Size (8)
        0x15.toByte(), 0x00.toByte(), //   Logical Minimum (0)
        0x25.toByte(), 0x65.toByte(), //   Logical Maximum (101)
        0x05.toByte(), 0x07.toByte(), //   Usage Page (Key Codes)
        0x19.toByte(), 0x00.toByte(), //   Usage Minimum (0)
        0x29.toByte(), 0x65.toByte(), //   Usage Maximum (101)
        0x81.toByte(), 0x00.toByte(), //   Input (Data, Array) ; Key array (6 bytes)
        0xC0.toByte()                 // End Collection
    )

    /**
     * Standard HID mouse (Report ID 2)
     */
    val MOUSE_DESCRIPTOR = byteArrayOf(
        0x05.toByte(), 0x01.toByte(), // Usage Page (Generic Desktop)
        0x09.toByte(), 0x02.toByte(), // Usage (Mouse)
        0xA1.toByte(), 0x01.toByte(), // Collection (Application)
        0x85.toByte(), 0x02.toByte(), //   Report ID (2)
        0x09.toByte(), 0x01.toByte(), //   Usage (Pointer)
        0xA1.toByte(), 0x00.toByte(), //   Collection (Physical)
        0x05.toByte(), 0x09.toByte(), //     Usage Page (Button)
        0x19.toByte(), 0x01.toByte(), //     Usage Minimum (1)
        0x29.toByte(), 0x03.toByte(), //     Usage Maximum (3)
        0x15.toByte(), 0x00.toByte(), //     Logical Minimum (0)
        0x25.toByte(), 0x01.toByte(), //     Logical Maximum (1)
        0x95.toByte(), 0x03.toByte(), //     Report Count (3)
        0x75.toByte(), 0x01.toByte(), //     Report Size (1)
        0x81.toByte(), 0x02.toByte(), //     Input (Data, Variable, Absolute)
        0x95.toByte(), 0x01.toByte(), //     Report Count (1)
        0x75.toByte(), 0x05.toByte(), //     Report Size (5)
        0x81.toByte(), 0x01.toByte(), //     Input (Constant)
        0x05.toByte(), 0x01.toByte(), //     Usage Page (Generic Desktop)
        0x09.toByte(), 0x30.toByte(), //     Usage (X)
        0x09.toByte(), 0x31.toByte(), //     Usage (Y)
        0x16.toByte(), 0x00.toByte(), 0x80.toByte(), // Logical Minimum (-32768)
        0x26.toByte(), 0xFF.toByte(), 0x7F.toByte(), // Logical Maximum (32767)
        0x75.toByte(), 0x10.toByte(), //     Report Size (16)
        0x95.toByte(), 0x02.toByte(), //     Report Count (2)
        0x81.toByte(), 0x06.toByte(), //     Input (Data, Variable, Relative)
        0x09.toByte(), 0x38.toByte(), //     Usage (Wheel)
        0x15.toByte(), 0x81.toByte(), //     Logical Minimum (-127)
        0x25.toByte(), 0x7F.toByte(), //     Logical Maximum (127)
        0x75.toByte(), 0x08.toByte(), //     Report Size (8)
        0x95.toByte(), 0x01.toByte(), //     Report Count (1)
        0x81.toByte(), 0x06.toByte(), //     Input (Data, Variable, Relative)
        0xC0.toByte(),                //   End Collection
        0xC0.toByte()                 // End Collection
    )

    /**
     * HID gamepad (Report ID 3)
     */
    val GAMEPAD_DESCRIPTOR = byteArrayOf(
        0x05.toByte(), 0x01.toByte(), // Usage Page (Generic Desktop)
        0x09.toByte(), 0x05.toByte(), // Usage (Gamepad)
        0xA1.toByte(), 0x01.toByte(), // Collection (Application)
        0x85.toByte(), 0x03.toByte(), //   Report ID (3)
        
        // Buttons (16 buttons)
        0x05.toByte(), 0x09.toByte(), //   Usage Page (Button)
        0x19.toByte(), 0x01.toByte(), //   Usage Minimum (1)
        0x29.toByte(), 0x10.toByte(), //   Usage Maximum (16)
        0x15.toByte(), 0x00.toByte(), //   Logical Minimum (0)
        0x25.toByte(), 0x01.toByte(), //   Logical Maximum (1)
        0x75.toByte(), 0x01.toByte(), //   Report Size (1)
        0x95.toByte(), 0x10.toByte(), //   Report Count (16)
        0x81.toByte(), 0x02.toByte(), //   Input (Data, Variable, Absolute)
        
        // Hat switch (D-pad)
        0x05.toByte(), 0x01.toByte(), //   Usage Page (Generic Desktop)
        0x09.toByte(), 0x39.toByte(), //   Usage (Hat switch)
        0x15.toByte(), 0x01.toByte(), //   Logical Minimum (1)
        0x25.toByte(), 0x08.toByte(), //   Logical Maximum (8)
        0x35.toByte(), 0x00.toByte(), //   Physical Minimum (0)
        0x46.toByte(), 0x3B.toByte(), 0x01.toByte(), // Physical Maximum (315)
        0x65.toByte(), 0x14.toByte(), //   Unit (English Rotation/Angular Position)
        0x75.toByte(), 0x04.toByte(), //   Report Size (4)
        0x95.toByte(), 0x01.toByte(), //   Report Count (1)
        0x81.toByte(), 0x02.toByte(), //   Input (Data, Variable, Absolute)
        
        // Padding for Hat switch
        0x75.toByte(), 0x04.toByte(), //   Report Size (4)
        0x95.toByte(), 0x01.toByte(), //   Report Count (1)
        0x81.toByte(), 0x03.toByte(), //   Input (Constant, Variable, Absolute)
        
        // Joysticks (LX, LY, RX, RY) - 16-bit
        0x05.toByte(), 0x01.toByte(), //   Usage Page (Generic Desktop)
        0x09.toByte(), 0x30.toByte(), //   Usage (X)
        0x09.toByte(), 0x31.toByte(), //   Usage (Y)
        0x09.toByte(), 0x32.toByte(), //   Usage (Z)
        0x09.toByte(), 0x35.toByte(), //   Usage (Rz)
        0x16.toByte(), 0x00.toByte(), 0x80.toByte(), // Logical Minimum (-32768)
        0x26.toByte(), 0xFF.toByte(), 0x7F.toByte(), // Logical Maximum (32767)
        0x75.toByte(), 0x10.toByte(), //   Report Size (16)
        0x95.toByte(), 0x04.toByte(), //   Report Count (4)
        0x81.toByte(), 0x02.toByte(), //   Input (Data, Variable, Absolute)

        // Triggers (L2, R2) - 8-bit
        0x05.toByte(), 0x02.toByte(), //   Usage Page (Simulation Controls)
        0x09.toByte(), 0xC4.toByte(), //   Usage (Accelerator) - roughly right trigger
        0x09.toByte(), 0xC5.toByte(), //   Usage (Brake) - roughly left trigger
        0x15.toByte(), 0x00.toByte(), //   Logical Minimum (0)
        0x26.toByte(), 0xFF.toByte(), 0x00.toByte(), // Logical Maximum (255)
        0x75.toByte(), 0x08.toByte(), //   Report Size (8)
        0x95.toByte(), 0x02.toByte(), //   Report Count (2)
        0x81.toByte(), 0x02.toByte(), //   Input (Data, Variable, Absolute)
        
        0xC0.toByte()                 // End Collection
    )

    val COMBINED_DESCRIPTOR = KEYBOARD_DESCRIPTOR + MOUSE_DESCRIPTOR + GAMEPAD_DESCRIPTOR
}
