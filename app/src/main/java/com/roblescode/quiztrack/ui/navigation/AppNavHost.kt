package com.roblescode.quiztrack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.roblescode.quiztrack.ui.constants.Routes
import com.roblescode.quiztrack.ui.screens.auth.AuthScreen
import com.roblescode.quiztrack.ui.screens.auth.AuthViewModel
import com.roblescode.quiztrack.ui.screens.home.HomeScreen
import com.roblescode.quiztrack.ui.screens.home.QuizScreen
import com.roblescode.quiztrack.ui.screens.home.TriviaViewModel
import com.roblescode.quiztrack.ui.screens.splash.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navController: NavHostController,
    triviaViewModel: TriviaViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(Routes.AUTH) {
            AuthScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(Routes.HOME) {
            HomeScreen(
                navController = navController,
                triviaViewModel = triviaViewModel,
                authViewModel = authViewModel,
            )
        }

        composable(
            route = "${Routes.QUIZ}/{playlistId}",
            arguments = listOf(
                navArgument("playlistId") {
                    type = NavType.StringType
                }
            )
        ) {
            val playlistId = it.arguments?.getString("playlistId")?.toLongOrNull()
            if (playlistId != null) {
                QuizScreen(
                    playlistId = playlistId,
                    triviaViewModel = triviaViewModel,
                    authViewModel = authViewModel,
                    navController = navController
                )
            } else {
                navController.navigateUp()
            }
        }
    }
}