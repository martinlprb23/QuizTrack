package com.roblescode.quiztrack.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roblescode.quiztrack.R
import com.roblescode.quiztrack.data.utils.Response
import com.roblescode.quiztrack.ui.constants.Routes
import com.roblescode.quiztrack.ui.theme.primary

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    // authViewModel: AuthViewModel
) {
    // TODO Uncomment and import AuthViewModel
    /*val context = LocalContext.current
    val loginResponse = authViewModel.loginResponse.collectAsState()

    LaunchedEffect(key1 = loginResponse.value) {
        if (loginResponse.value is Response.Success) {
            navController.navigate(Routes.HOME) {
                popUpTo(Routes.AUTH) {
                    inclusive = true
                }
            }
        }

        if (loginResponse.value is Response.Failure) {
            val msg = (loginResponse.value as Response.Failure).exception?.message
            Toast.makeText(context, "Something went wrong: $msg", Toast.LENGTH_SHORT).show()
        }
    }*/

    Scaffold {
        Column(
            modifier = modifier
                .padding(it)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            IconApp()
            // TODO Uncomment
            /*AuthSection(
                enabled = loginResponse.value !is Response.Loading,
                onGoogleSignIn = {
                    // authViewModel.signInWithGoogle(context)
                }
            )
            StateSection(isLoading = loginResponse.value is Response.Loading)*/
        }
    }
}

@Composable
fun IconApp(modifier: Modifier = Modifier) {
    Box(modifier = modifier.height(300.dp)) {
        Image(
            painter = painterResource(R.drawable.quiz_track_logo),
            contentDescription = null
        )
    }
}


@Composable
fun AuthSection(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onGoogleSignIn: () -> Unit
) {

    val toText = stringResource(R.string.to)
    val appName = stringResource(R.string.app_name)

    Column {

        Text(stringResource(R.string.welcome), style = MaterialTheme.typography.displayMedium)

        Text(
            buildAnnotatedString {
                append("$toText ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.SemiBold,
                        color = primary
                    )
                ) {
                    append(appName)
                }
            },
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            stringResource(R.string.login_or_register),
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            enabled = enabled,
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = { onGoogleSignIn() }
        ) {
            Text(text = stringResource(R.string.google), fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(R.drawable.googlesvg),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
fun StateSection(modifier: Modifier = Modifier, isLoading: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = primary,
                strokeWidth = 2.dp,
                modifier = modifier.size(24.dp)
            )
        }
    }
}