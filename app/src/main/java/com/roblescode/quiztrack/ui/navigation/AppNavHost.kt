package com.roblescode.quiztrack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.roblescode.quiztrack.ui.constants.Routes
import com.roblescode.quiztrack.ui.screens.auth.AuthScreen
import com.roblescode.quiztrack.ui.screens.home.HomeScreen
import com.roblescode.quiztrack.ui.screens.home.QuizScreen
import com.roblescode.quiztrack.ui.screens.splash.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    //TODO: authViewModel: AuthViewModel,
    navController: NavHostController,
    //TODO: triviaViewModel: TriviaViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
        modifier = modifier
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(
                navController = navController,
                //TODO authViewModel = authViewModel
            )
        }

        composable(Routes.AUTH) {
            AuthScreen(
                navController = navController,
                //TODO authViewModel = authViewModel
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                navController = navController,
                //TODO triviaViewModel = triviaViewModel,
                //TODO authViewModel = authViewModel,
            )
        }


        composable<QuizRoute> {
            val args = it.toRoute<QuizRoute>()
            QuizScreen(
                args = args,
                //TODO: triviaViewModel = triviaViewModel,
                //TODO: authViewModel = authViewModel,
                navController = navController
            )
        }
    }
}