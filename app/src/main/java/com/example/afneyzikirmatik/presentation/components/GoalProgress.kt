package com.example.afneyzikirmatik.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.afneyzikirmatik.domain.model.Goal

@Composable
fun GoalProgress(
    goal: Goal,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Goal: ${goal.currentProgress}/${goal.target}",
            style = MaterialTheme.typography.titleMedium
        )
        LinearProgressIndicator(
            progress = (goal.currentProgress.toFloat() / goal.target).coerceIn(0f, 1f),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(8.dp)
                .padding(vertical = 8.dp)
        )
    }
}
