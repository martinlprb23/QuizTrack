package com.roblescode.quiztrack.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roblescode.quiztrack.R
import com.roblescode.quiztrack.data.model.response.GameData
import com.roblescode.quiztrack.data.utils.Response
import com.roblescode.quiztrack.domain.utils.AudioPlayer
import com.roblescode.quiztrack.ui.screens.auth.AuthViewModel
import com.roblescode.quiztrack.ui.screens.home.components.PlayAndPause
import com.roblescode.quiztrack.ui.screens.home.components.QuizOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    playlistId: String,
    triviaViewModel: TriviaViewModel,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val context = LocalContext.current
    val audioPlayer = remember { AudioPlayer(context) }
    val isPlaying by audioPlayer.isPlayingFlow.collectAsState()
    val playListCover by triviaViewModel.playlistCover.collectAsState()

    LaunchedEffect(Unit) {
        triviaViewModel.findPlaylistCover(playlistId)
        triviaViewModel.startGame(authViewModel.currentUser, playlistId)
    }

    DisposableEffect(Unit) {
        onDispose {
            audioPlayer.release()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.quiz)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Rounded.ArrowBackIosNew, null)
                    }
                }
            )
        }
    ) {
        Box(
            modifier = modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            when (val gameResponse = triviaViewModel.gameResponse.collectAsState().value) {
                is Response.Loading -> CircularProgressIndicator()

                is Response.Success -> GameSection(
                    gameData = gameResponse.data,
                    isAudioPlaying = isPlaying,
                    onPlayAudio = { url -> audioPlayer.play(url) },
                    onPauseAudio = { audioPlayer.pause() },
                    playListCover = playListCover,
                )

                is Response.Failure -> Text(
                    text = gameResponse.exception?.message ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun GameSection(
    modifier: Modifier = Modifier,
    gameData: GameData,
    isAudioPlaying: Boolean,
    onPlayAudio: (String) -> Unit,
    onPauseAudio: () -> Unit,
    playListCover: String?,
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentQuestion = gameData.questions.getOrNull(currentIndex)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        currentQuestion?.let { question ->
            if (question.previewUrl.isNotEmpty()) {

                PlayAndPause(
                    isAudioPlaying = isAudioPlaying,
                    onPlayAudio = { onPlayAudio(question.previewUrl) },
                    onPauseAudio = onPauseAudio,
                    playListCover = playListCover
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "${currentIndex + 1}/${gameData.totalQuestions}",
                    style = MaterialTheme.typography.titleMedium
                )

            }

            Spacer(modifier = Modifier.height(24.dp))

            QuizOptions(
                question = question,
                onOptionSelected = { option ->
                    currentIndex++
                    if (currentIndex <= gameData.questions.lastIndex) {
                        onPlayAudio(gameData.questions[currentIndex].previewUrl)
                    }
                }
            )
        } ?: run {
            Text("¡Juego terminado!")
        }
    }
}

