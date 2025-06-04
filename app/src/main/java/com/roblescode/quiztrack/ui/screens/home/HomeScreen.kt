package com.roblescode.quiztrack.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.roblescode.quiztrack.R
import com.roblescode.quiztrack.data.model.response.Playlist
import com.roblescode.quiztrack.data.utils.Response
import com.roblescode.quiztrack.ui.constants.Routes
import com.roblescode.quiztrack.ui.navigation.QuizRoute
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    //triviaViewModel: TriviaViewModel,
    //authViewModel: AuthViewModel,
) {

    /*LaunchedEffect(authViewModel.loginResponse.collectAsState().value) {
        val user = authViewModel.loginResponse.value
        if (user is Response.Success) {
            triviaViewModel.setCurrentUser(user.data)
            triviaViewModel.getPlaylists()
        }
    }*/


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    AsyncImage(
                        model = null,
                        //TODO: model = authViewModel.currentUser?.photoUrl,
                        contentDescription = null,
                        //TODO contentDescription = authViewModel.currentUser?.displayName,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .padding(start = 16.dp)
                            .size(30.dp)
                            .clip(CircleShape)
                            .border(width = 1.dp, shape = CircleShape, color = Color.Gray)
                    )
                },
                title = { Text(stringResource(R.string.playlists)) },
                actions = {
                    IconButton(onClick = {
                        //TODO: authViewModel.logout()
                        navController.navigate(Routes.AUTH) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Rounded.Close, null)
                    }
                }
            )
        }
    ) {
        Box(
            modifier = modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            /*when (val response = triviaViewModel.playlistResponse.collectAsState().value) {
                is Response.Loading -> CircularProgressIndicator()
                is Response.Success -> PlaylistsList(
                    list = response.data,
                    onClick = { playlist ->
                        navController.navigate(
                            QuizRoute(
                                playlistId = playlist.id,
                                coverImageUrl = playlist.picture
                            )
                        )
                    }
                )

                is Response.Failure -> PlaylistsError(errorText = response.exception?.message)
            }*/
        }
    }
}

@Composable
fun PlaylistsList(
    modifier: Modifier = Modifier,
    list: List<Playlist>,
    onClick: (playlist: Playlist) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(list.size, key = { index -> list[index].id }) { index ->
            val playlist = list[index]

            val stateVisible = remember {
                MutableTransitionState(false).apply {
                    targetState = true
                }
            }

            LaunchedEffect(index) {
                delay(index * 50L)
                stateVisible.targetState = true
            }

            AnimatedVisibility(
                visibleState = stateVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 50)) + scaleIn(),
                exit = fadeOut(animationSpec = tween(durationMillis = 25))
            ) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.large)
                        .clickable(onClick = { onClick(playlist) })
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(playlist.picture)
                            .crossfade(true)
                            .build(),
                        contentDescription = playlist.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        playlist.title,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        modifier = modifier
                            .align(Alignment.BottomStart)
                            .padding(4.dp)
                            .background(Color.Black.copy(alpha = 0.75f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PlaylistsError(modifier: Modifier = Modifier, errorText: String?) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(errorText ?: "Unknown error")
    }
}