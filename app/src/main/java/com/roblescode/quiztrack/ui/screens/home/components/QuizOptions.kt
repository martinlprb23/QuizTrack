package com.roblescode.quiztrack.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.roblescode.quiztrack.data.model.response.Option
import com.roblescode.quiztrack.data.model.response.Question

@Composable
fun QuizOptions(
    modifier: Modifier = Modifier,
    question: Question,
    onOptionSelected: (Option) -> Unit,
    enabled: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        question.options.forEach { option ->
            Button(
                enabled = enabled,
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