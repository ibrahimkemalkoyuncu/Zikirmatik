package com.example.afneyzikirmatik.data.repository

import com.example.afneyzikirmatik.data.local.PreferencesManager
import com.example.afneyzikirmatik.domain.model.CounterState
import com.example.afneyzikirmatik.domain.model.Goal
import com.example.afneyzikirmatik.domain.model.Streak
import kotlinx.coroutines.flow.Flow

/**
 * Repository for counter operations
 * Acts as a single source of truth for counter data
 * Handles all DataStore persistence
 */
class CounterRepository(private val preferencesManager: PreferencesManager) {

    // Reactive flows - used by ViewModels
    fun getCounterState(): Flow<CounterState> = preferencesManager.counterStateFlow

    fun getGoal(): Flow<Goal> = preferencesManager.goalFlow

    fun getStreak(): Flow<Streak> = preferencesManager.streakFlow

    // Save operations - used by use cases
    suspend fun saveCounterState(state: CounterState) {
        preferencesManager.saveCounterState(state)
    }

    suspend fun saveGoalTarget(target: Int) {
        preferencesManager.saveGoalTarget(target)
    }

    suspend fun saveStreak(streak: Streak) {
        preferencesManager.saveStreak(streak)
    }

    // Utility methods
    suspend fun isFirstLaunch(): Boolean {
        return preferencesManager.isFirstLaunch()
    }

    suspend fun getAllData(): Map<String, Any> {
        return preferencesManager.getAllData()
    }

    suspend fun resetAll() {
        preferencesManager.clearAllData()
    }
}
