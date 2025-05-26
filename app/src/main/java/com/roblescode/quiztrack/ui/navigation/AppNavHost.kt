package com.roblescode.quiztrack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.roblescode.quiztrack.ui.constants.Routes
import com.roblescode.quiztrack.ui.screens.splash.SplashScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
        modifier = modifier
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(navController = navController)
        }

    }
}