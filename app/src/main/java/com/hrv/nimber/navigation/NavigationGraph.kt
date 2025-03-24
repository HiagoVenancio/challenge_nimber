package com.hrv.nimber.navigation

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.hrv.nimber.presentation.ui.CreateReceiptScreen
import com.hrv.nimber.presentation.ui.ReceiptDetails
import com.hrv.nimber.presentation.ui.ReceiptListScreen
import com.hrv.nimber.presentation.viewmodel.ConfigurationViewModel
import com.hrv.nimber.presentation.viewmodel.ReceiptViewModel



sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object CreateReceiptScreen : Screen("create_receipt_screen")
    object DetailsReceiptScreen : Screen("receipt/{itemId}") {
        fun createRoute(itemId: String) = "receipt/$itemId"
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(
    navController: NavHostController,
    configurationViewModel: ConfigurationViewModel,
    receiptViewModel: ReceiptViewModel,
    permissionLauncher: ActivityResultLauncher<Array<String>>
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut() },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn() },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
    ) {

        composable(Screen.MainScreen.route) {
            ReceiptListScreen(
                navController = navController,
                receiptViewModel,
                configurationViewModel,
                onAddClick = {
                    navController.navigate(Screen.CreateReceiptScreen.route)
                })
        }

        composable(Screen.CreateReceiptScreen.route) {
            CreateReceiptScreen(
                navController = navController,
                receiptViewModel,
                configurationViewModel,
                permissionLauncher
            )
        }

        composable(Screen.DetailsReceiptScreen.route) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: return@composable
            ReceiptDetails(
                navController,
                itemId = itemId.toInt(),
                receiptViewModel,
                configurationViewModel
            )
        }
    }
}
