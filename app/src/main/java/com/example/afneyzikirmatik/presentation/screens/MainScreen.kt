package com.example.afneyzikirmatik.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.afneyzikirmatik.presentation.components.*
import com.example.afneyzikirmatik.presentation.viewmodel.ZikirmatikViewModel

@Composable
fun MainScreen(
    viewModel: ZikirmatikViewModel = viewModel()
) {
    val counterState by viewModel.counterState.collectAsState()
    val zikrs by viewModel.zikrs.collectAsState()
    val goal by viewModel.goal.collectAsState()
    val streak by viewModel.streak.collectAsState()
    val currentZikr by viewModel.currentZikr.collectAsState()
    val milestone by viewModel.milestone.collectAsState()

    var showZikrSelector by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Top spacing
            Spacer(modifier = Modifier.height(48.dp))

            // Current Zikr Display
            currentZikr?.let { zikr ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = zikr.arabic,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = zikr.translation,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Milestone Feedback
            milestone?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Counter Button
            CounterButton(
                count = counterState.count,
                onIncrement = viewModel::incrementCounter
            )

            // Change Zikr Button
            OutlinedButton(
                onClick = { showZikrSelector = true },
                modifier = Modifier.padding(vertical = 16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Change Dhikr")
            }

            Spacer(modifier = Modifier.weight(1f))

            // Goal Progress
            GoalProgress(goal = goal)

            // Streak Display
            StreakDisplay(streak = streak)
        }
    }

    // Zikr Selector Modal
    if (showZikrSelector) {
        ZikrSelector(
            zikrs = zikrs,
            currentZikrId = counterState.currentZikrId,
            onZikrSelected = { zikrId ->
                viewModel.switchZikr(zikrId)
                showZikrSelector = false
            }
        )
    }
}
