package com.hrv.nimber.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.hrv.nimber.presentation.ui.CreateReceiptScreen
import com.hrv.nimber.presentation.ui.ReceiptDetails
import com.hrv.nimber.presentation.ui.ReceiptListScreen
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
) {
    val receiptViewModel: ReceiptViewModel = hiltViewModel()

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
                onAddClick = {
                    navController.navigate(Screen.CreateReceiptScreen.route)
                })
        }

        composable(Screen.CreateReceiptScreen.route) {
            CreateReceiptScreen(
                navController = navController,
                receiptViewModel
            )
        }

        composable(Screen.DetailsReceiptScreen.route) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: return@composable
            ReceiptDetails(
                itemId = itemId.toInt(),
                receiptViewModel
            )
        }
    }
}
