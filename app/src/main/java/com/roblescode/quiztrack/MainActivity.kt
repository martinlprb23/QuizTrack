package com.roblescode.quiztrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.roblescode.quiztrack.data.api.TriviaApiClient.triviaApi
import com.roblescode.quiztrack.data.repository.AuthRepositoryImpl
import com.roblescode.quiztrack.ui.navigation.AppNavHost
import com.roblescode.quiztrack.ui.screens.auth.AuthViewModel
import com.roblescode.quiztrack.ui.theme.QuizTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val authRepository = AuthRepositoryImpl(
            auth = FirebaseAuth.getInstance(),
            triviaApi = triviaApi
        )
        val authViewModel = AuthViewModel(authRepository = authRepository)

        setContent {
            QuizTrackTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(
                        modifier = Modifier.padding(innerPadding),
                        authViewModel = authViewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}