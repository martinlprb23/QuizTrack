package com.roblescode.quiztrack.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.roblescode.quiztrack.data.model.request.Answer
import com.roblescode.quiztrack.data.model.response.GameData
import com.roblescode.quiztrack.data.model.response.Playlist
import com.roblescode.quiztrack.data.model.response.SubmitAnswerData
import com.roblescode.quiztrack.data.utils.Response
import com.roblescode.quiztrack.domain.repository.TriviaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TriviaViewModel(
    private val triviaRepo: TriviaRepository,
) : ViewModel() {

    var user: FirebaseUser? = null

    fun setCurrentUser(currentUser: FirebaseUser?) {
        user = currentUser
    }


    private val _playlistsResponse = MutableStateFlow<Response<List<Playlist>>>(Response.Loading)
    val playlistResponse: StateFlow<Response<List<Playlist>>> = _playlistsResponse

    fun getPlaylists() {
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

    fun startGame(user: FirebaseUser?, playlistId: Long) {
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

    private val _answerResponse = MutableStateFlow<Response<SubmitAnswerData>>(Response.Loading)
    val answerResponse: StateFlow<Response<SubmitAnswerData>> = _answerResponse


    fun sendAnswer(sessionId: String, answer: Answer) {
        _answerResponse.value = Response.Loading
        viewModelScope.launch {
            val token = user?.getIdToken(true)?.await()?.token
            val uid = user?.uid

            if (token == null || uid == null) {
                _answerResponse.value = Response.Failure(Exception("Unauthorized"))
            } else {
                _answerResponse.value = triviaRepo.submitAnswer(
                    token = token,
                    sessionId = sessionId,
                    answer = answer
                )
            }
        }
    }


    private val _playlistCover = MutableStateFlow<String?>(null)
    val playlistCover: StateFlow<String?> = _playlistCover

    fun findPlaylistCover(playlistId: Long) {
        if (_playlistsResponse.value is Response.Success) {
            val response = _playlistsResponse.value as Response.Success
            val playlist = response.data.find { it.id == playlistId }
            _playlistCover.value = playlist?.picture
        }
    }


}