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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.roblescode.quiztrack.data.api.TriviaApiClient.triviaApi
import com.roblescode.quiztrack.data.repository.AuthRepositoryImpl
import com.roblescode.quiztrack.data.repository.TriviaRepositoryImpl
import com.roblescode.quiztrack.ui.navigation.AppNavHost
import com.roblescode.quiztrack.ui.screens.auth.AuthViewModel
import com.roblescode.quiztrack.ui.screens.home.TriviaViewModel
import com.roblescode.quiztrack.ui.theme.QuizTrackTheme

@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val authRepository = AuthRepositoryImpl(
            auth = FirebaseAuth.getInstance()
        )

        val triviaRepository = TriviaRepositoryImpl(
            triviaApi = triviaApi
        )

        setContent {

            val authViewModel: AuthViewModel = viewModel(
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
            )


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
                        authViewModel = authViewModel,
                        triviaViewModel = triviaViewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}