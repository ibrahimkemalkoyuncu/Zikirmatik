package com.example.afneyzikirmatik.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.afneyzikirmatik.domain.model.Goal
import com.example.afneyzikirmatik.ui.theme.EmeraldPrimary
import com.example.afneyzikirmatik.ui.theme.EmeraldPrimaryVariant

@Composable
fun GoalProgress(
    goal: Goal,
    modifier: Modifier = Modifier
) {
    val progress by animateFloatAsState(
        targetValue = (goal.currentProgress.toFloat() / goal.target).coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 800)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Daily Goal: ${goal.currentProgress}/${goal.target}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = progress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                EmeraldPrimaryVariant,
                                EmeraldPrimary
                            )
                        )
                    )
            )
        }

        if (goal.currentProgress >= goal.target) {
            Text(
                text = "🎉 Goal Achieved!",
                style = MaterialTheme.typography.labelLarge,
                color = EmeraldPrimary
            )
        }
    }
}
