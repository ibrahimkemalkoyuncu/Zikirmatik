package com.example.afneyzikirmatik.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.afneyzikirmatik.presentation.components.*
import com.example.afneyzikirmatik.presentation.viewmodel.ZikirmatikViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

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

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Current Zikr Display
            currentZikr?.let { zikr ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = zikr.arabic,
                            style = MaterialTheme.typography.displayMedium
                        )
                        Text(
                            text = zikr.translation,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
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

            // Zikr Selector
            ZikrSelector(
                zikrs = zikrs,
                currentZikrId = counterState.currentZikrId,
                onZikrSelected = viewModel::switchZikr
            )

            Spacer(modifier = Modifier.weight(1f))

            // Goal Progress
            GoalProgress(goal = goal)

            // Streak Display
            StreakDisplay(streak = streak)
        }
    }
}
