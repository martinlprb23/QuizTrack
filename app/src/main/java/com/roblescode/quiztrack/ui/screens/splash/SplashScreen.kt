package com.roblescode.quiztrack.ui.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roblescode.quiztrack.R
import com.roblescode.quiztrack.ui.constants.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    //authViewModel: AuthViewModel
) {

    /*LaunchedEffect(key1 = Unit) {
        delay(1000)
        withContext(Dispatchers.Main) {
            if (authViewModel.currentUser != null) {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.SPLASH) {
                        inclusive = true
                    }
                }
            } else {
                navController.navigate(Routes.AUTH) {
                    popUpTo(Routes.SPLASH) {
                        inclusive = true
                    }
                }
            }
        }
    }*/


    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )
            LinearProgressIndicator(modifier = modifier.width(80.dp))
        }
    }
}