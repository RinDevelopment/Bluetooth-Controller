package com.bluetoothcontroller.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bluetoothcontroller.data.CompatibilityChecker
import com.bluetoothcontroller.ui.screens.*

object Routes {
    const val ROLE_SELECTION = "role_selection"
    const val HOME = "home"
    const val CONTROLLER = "controller"
    const val KEYBOARD = "keyboard"
    const val MOUSE = "mouse"
    const val PAIRING = "pairing"
    const val SETTINGS = "settings"
    const val LAYOUT_EDITOR = "layout_editor"
    const val RECEIVER = "receiver"
    const val COMPATIBILITY = "compatibility"
}

@Composable
fun BluetoothControllerNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.ROLE_SELECTION
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.ROLE_SELECTION) {
            RoleSelectionScreen(
                onNavigateToHome = { navController.navigate(Routes.HOME) },
                onNavigateToReceiver = { navController.navigate(Routes.RECEIVER) },
                onNavigateToCompatibility = { navController.navigate(Routes.COMPATIBILITY) }
            )
        }
        
        composable(Routes.HOME) {
            HomeScreen(
                viewModel = viewModel(),
                onNavigateToController = { navController.navigate(Routes.CONTROLLER) },
                onNavigateToKeyboard = { navController.navigate(Routes.KEYBOARD) },
                onNavigateToMouse = { navController.navigate(Routes.MOUSE) },
                onNavigateToPairing = { navController.navigate(Routes.PAIRING) },
                onNavigateToSettings = { navController.navigate(Routes.SETTINGS) },
                onNavigateToCompatibility = { navController.navigate(Routes.COMPATIBILITY) }
            )
        }
        
        composable(Routes.CONTROLLER) {
            ControllerScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSettings = { navController.navigate(Routes.SETTINGS) }
            )
        }
        
        composable(Routes.KEYBOARD) {
            KeyboardScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSettings = { navController.navigate(Routes.SETTINGS) }
            )
        }
        
        composable(Routes.MOUSE) {
            MouseScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSettings = { navController.navigate(Routes.SETTINGS) }
            )
        }
        
        composable(Routes.PAIRING) {
            PairingScreen(
                viewModel = viewModel(),
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.SETTINGS) {
            SettingsScreen(
                viewModel = viewModel(),
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.LAYOUT_EDITOR) {
            LayoutEditorScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.RECEIVER) {
            ReceiverScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.COMPATIBILITY) {
            val context = LocalContext.current
            val compatibilityResult = remember { CompatibilityChecker.checkCompatibility(context) }
            CompatibilityScreen(
                compatibilityResult = compatibilityResult,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
