package com.bluetoothcontroller

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bluetoothcontroller.bluetooth.BluetoothPermissions
import com.bluetoothcontroller.navigation.BluetoothControllerNavGraph
import com.bluetoothcontroller.ui.theme.BluetoothControllerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Hide system bars for immersive controller mode
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        
        setContent {
            BluetoothControllerTheme {
                val context = LocalContext.current
                val app = context.applicationContext as BluetoothControllerApp
                
                var hasPermissions by remember {
                    mutableStateOf(BluetoothPermissions.hasRequiredPermissions(context))
                }
                
                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions()
                ) { results ->
                    val allGranted = results.values.all { it }
                    hasPermissions = allGranted
                    if (allGranted) {
                        app.bluetoothManager.initialize(context)
                    } else {
                        Toast.makeText(
                            context,
                            "Bluetooth permissions are required to connect devices",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                
                LaunchedEffect(Unit) {
                    if (!hasPermissions) {
                        permissionLauncher.launch(BluetoothPermissions.getRequiredPermissions().toTypedArray())
                    } else {
                        app.bluetoothManager.initialize(context)
                    }
                }
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BluetoothControllerNavGraph()
                }
            }
        }
    }
}
