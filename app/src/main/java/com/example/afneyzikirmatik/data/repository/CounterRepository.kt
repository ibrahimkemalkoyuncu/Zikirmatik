package com.example.afneyzikirmatik.data.repository

import com.example.afneyzikirmatik.data.local.PreferencesManager
import com.example.afneyzikirmatik.domain.model.CounterState
import com.example.afneyzikirmatik.domain.model.Goal
import com.example.afneyzikirmatik.domain.model.Streak
import kotlinx.coroutines.flow.Flow

class CounterRepository(private val preferencesManager: PreferencesManager) {

    fun getCounterState(): Flow<CounterState> = preferencesManager.counterStateFlow

    fun getGoal(): Flow<Goal> = preferencesManager.goalFlow

    fun getStreak(): Flow<Streak> = preferencesManager.streakFlow

    suspend fun saveCounterState(state: CounterState) {
        preferencesManager.saveCounterState(state)
    }

    suspend fun saveGoalTarget(target: Int) {
        preferencesManager.saveGoalTarget(target)
    }

    suspend fun saveStreak(streak: Streak) {
        preferencesManager.saveStreak(streak)
    }
}
