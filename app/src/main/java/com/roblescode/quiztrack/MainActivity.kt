package com.roblescode.quiztrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.roblescode.quiztrack.ui.navigation.AppNavHost
import com.roblescode.quiztrack.ui.theme.QuizTrackTheme

@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            // TODO: Uncomment
            /*val authViewModel: AuthViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return AuthViewModel(authRepository) as T
                    }
                }
            )

            val triviaViewModel: TriviaViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return TriviaViewModel(triviaRepository) as T
                    }
                }
            )*/

            QuizTrackTheme {
                val navController = rememberNavController()
                Scaffold(
                    contentWindowInsets = WindowInsets.ime,
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    AppNavHost(
                        modifier = Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding),
                        //authViewModel = authViewModel,
                        //triviaViewModel = triviaViewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}