package com.roblescode.quiztrack.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.roblescode.quiztrack.R
import com.roblescode.quiztrack.data.api.TriviaApiService
import com.roblescode.quiztrack.data.model.LoginRequest
import com.roblescode.quiztrack.data.utils.Response
import com.roblescode.quiztrack.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val triviaApi: TriviaApiService
) : AuthRepository {

    companion object {
        const val TAG = "AuthRepository"
    }

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun googleSignIn(context: Context): Response<FirebaseUser> {
        return try {
            val credentialManager: CredentialManager = CredentialManager.create(context)

            val ranNonce = UUID.randomUUID().toString()
            val hashedNonce = MessageDigest.getInstance("SHA-256")
                .digest(ranNonce.toByteArray())
                .joinToString("") { "%02x".format(it) }

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .setNonce(hashedNonce)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(context, request)
            val credential = result.credential

            val typeRequired = GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            if (credential is CustomCredential && credential.type == typeRequired) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken

                val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                val authResult = auth.signInWithCredential(googleCredential).await()
                val userTokenId = authResult.user!!.getIdToken(true).await()

                val loginRequest = LoginRequest(userTokenId.token!!)
                val response = triviaApi.login(loginRequest)

                if (!response.success) {
                    throw Exception(response.message)
                }

                Log.d(TAG, response.message)
                Response.Success(authResult.user!!)
            } else {
                throw Exception("Invalid credential type")
            }
        } catch (e: Exception) {
            Log.d(TAG, "googleSignIn: ${e.message}")
            Response.Failure(e)
        }
    }

    override suspend fun logout(): Response<Unit> {
        return try {
            auth.signOut()
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


}