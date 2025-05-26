package com.roblescode.quiztrack.domain.repository

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.roblescode.quiztrack.data.utils.Response

interface AuthRepository {

    val currentUser: FirebaseUser?

    suspend fun googleSignIn(context: Context): Response<FirebaseUser>

    suspend fun logout(): Response<Unit>

}