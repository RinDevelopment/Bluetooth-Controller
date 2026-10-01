package com.bluetoothcontroller.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.bluetoothcontroller.BluetoothControllerApp
import com.bluetoothcontroller.bluetooth.ConnectionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class BluetoothHidService : Service() {

    private val binder = LocalBinder()
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    private val bluetoothManager by lazy {
        (application as BluetoothControllerApp).bluetoothManager
    }

    inner class LocalBinder : Binder() {
        fun getService(): BluetoothHidService = this@BluetoothHidService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        
        serviceScope.launch {
            bluetoothManager.connectionState.collect { state ->
                val device = bluetoothManager.connectedDevice.value
                updateConnectionState(state, device?.name)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_DISCONNECT) {
            disconnect()
            stopSelf()
            return START_NOT_STICKY
        }

        startForegroundServiceWithNotification()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        disconnect()
        bluetoothManager.cleanup(this)
        serviceScope.cancel()
    }

    private fun startForegroundServiceWithNotification() {
        val notification = createNotification("Waiting for connection")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_ID, 
                notification, 
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE
                } else {
                    0
                }
            )
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Bluetooth Controller",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows Bluetooth connection status"
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(text: String): Notification {
        val disconnectIntent = Intent(this, BluetoothHidService::class.java).apply {
            action = ACTION_DISCONNECT
        }
        val pendingDisconnectIntent = PendingIntent.getService(
            this,
            0,
            disconnectIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Bluetooth Controller")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.stat_sys_data_bluetooth)
            .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Disconnect", pendingDisconnectIntent)
            .setOngoing(true)
            .build()
    }

    private fun updateConnectionState(state: ConnectionState, deviceName: String? = null) {
        val text = when (state) {
            ConnectionState.CONNECTED -> "Connected to ${deviceName ?: "device"}"
            ConnectionState.CONNECTING -> "Connecting to ${deviceName ?: "device"}..."
            ConnectionState.RECONNECTING -> "Reconnecting..."
            ConnectionState.ERROR -> "Connection error"
            ConnectionState.DISCONNECTED -> "Disconnected"
        }
        
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, createNotification(text))

        if (state == ConnectionState.DISCONNECTED) {
            stopSelf()
        }
    }
    
    fun disconnect() {
        bluetoothManager.disconnect()
    }

    companion object {
        private const val CHANNEL_ID = "bluetooth_hid_channel"
        private const val NOTIFICATION_ID = 1
        const val ACTION_DISCONNECT = "com.bluetoothcontroller.action.DISCONNECT"
    }
}
