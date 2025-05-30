package com.roblescode.quiztrack.ui.screens.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.roblescode.quiztrack.data.utils.Response
import com.roblescode.quiztrack.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val currentUser = authRepository.currentUser

    private val _loginResponse = MutableStateFlow<Response<FirebaseUser>?>(null)
    val loginResponse: MutableStateFlow<Response<FirebaseUser>?> = _loginResponse

    fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            _loginResponse.value = Response.Loading
            _loginResponse.value = authRepository.googleSignIn(context)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _loginResponse.value = null
        }
    }

}