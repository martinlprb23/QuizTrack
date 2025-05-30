package com.roblescode.quiztrack.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.roblescode.quiztrack.data.model.response.GameData
import com.roblescode.quiztrack.data.model.response.Playlist
import com.roblescode.quiztrack.data.utils.Response
import com.roblescode.quiztrack.domain.repository.AuthRepository
import com.roblescode.quiztrack.domain.repository.TriviaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TriviaViewModel(
    private val triviaRepo: TriviaRepository,
) : ViewModel() {


    private val _playlistsResponse = MutableStateFlow<Response<List<Playlist>>>(Response.Loading)
    val playlistResponse: StateFlow<Response<List<Playlist>>> = _playlistsResponse

    fun getPlaylists(user: FirebaseUser?, forceRefresh: Boolean = false) {

        if (!forceRefresh) {
            if (_playlistsResponse.value is Response.Success) {
                return
            }
        }

        viewModelScope.launch {
            val token = user?.getIdToken(true)?.await()?.token
            if (token == null) {
                _playlistsResponse.value = Response.Failure(Exception("Token is null"))
            } else {
                _playlistsResponse.value = triviaRepo.getPlaylists(token)
            }
        }
    }

    private val _gameResponse = MutableStateFlow<Response<GameData>>(Response.Loading)
    val gameResponse: StateFlow<Response<GameData>> = _gameResponse

    fun startGame(user: FirebaseUser?, playlistId: String) {
        _gameResponse.value = Response.Loading
        viewModelScope.launch {
            val token = user?.getIdToken(true)?.await()?.token
            val uid = user?.uid

            if (token == null || uid == null) {
                _gameResponse.value = Response.Failure(Exception("Unauthorized"))
            } else {
                _gameResponse.value = triviaRepo.startGame(
                    uid = uid,
                    token = token,
                    playlistId = playlistId
                )
            }
        }
    }


    private val _playlistCover = MutableStateFlow<String?>(null)
    val playlistCover: StateFlow<String?> = _playlistCover

    fun findPlaylistCover(playlistId: String) {
        if (_playlistsResponse.value is Response.Success) {
            val response = _playlistsResponse.value as Response.Success
            val playlist = response.data.find { it.id == playlistId.toLong() }
            _playlistCover.value = playlist?.picture
        }
    }
}