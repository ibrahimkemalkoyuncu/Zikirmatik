package com.example.afneyzikirmatik.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.afneyzikirmatik.domain.model.Streak

@Composable
fun StreakDisplay(
    streak: Streak,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "🔥 ${streak.currentStreak} day streak",
            style = MaterialTheme.typography.titleMedium
        )
    }
}
