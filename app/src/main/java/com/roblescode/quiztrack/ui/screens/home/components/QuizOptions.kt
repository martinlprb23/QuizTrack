package com.roblescode.quiztrack.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Bottom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.roblescode.quiztrack.data.model.response.Option
import com.roblescode.quiztrack.data.model.response.Question

@Composable
fun QuizOptions(
    modifier: Modifier = Modifier,
    question: Question,
    onOptionSelected: (Option) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        question.options.forEach { option ->
            Button(
                onClick = { onOptionSelected(option) },
                modifier = modifier.fillMaxWidth(),
            ) {
                Text(
                    text = option.text,
                    modifier.padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}