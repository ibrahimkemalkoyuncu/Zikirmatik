package com.example.afneyzikirmatik.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.afneyzikirmatik.domain.model.CounterState
import com.example.afneyzikirmatik.domain.model.Goal
import com.example.afneyzikirmatik.domain.model.Streak
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "zikirmatik_prefs")

class PreferencesManager(private val context: Context) {

    // Preference Keys
    private companion object {
        val CURRENT_ZIKR_ID = stringPreferencesKey("current_zikr_id")
        val COUNT = intPreferencesKey("count")
        val GOAL_TARGET = intPreferencesKey("goal_target")
        val CURRENT_STREAK = intPreferencesKey("current_streak")
        val LAST_ACTIVE_DATE = longPreferencesKey("last_active_date")
        val APP_FIRST_LAUNCH = booleanPreferencesKey("app_first_launch")
        val LAST_BACKUP_DATE = longPreferencesKey("last_backup_date")
    }

    // StateFlow exposed flows for reactive updates
    val counterStateFlow: Flow<CounterState> = context.dataStore.data.map { prefs ->
        CounterState(
            currentZikrId = prefs[CURRENT_ZIKR_ID] ?: "subhanallah",
            count = prefs[COUNT] ?: 0
        )
    }

    val goalFlow: Flow<Goal> = context.dataStore.data.map { prefs ->
        Goal(
            target = prefs[GOAL_TARGET] ?: 100,
            currentProgress = prefs[COUNT] ?: 0
        )
    }

    val streakFlow: Flow<Streak> = context.dataStore.data.map { prefs ->
        Streak(
            currentStreak = prefs[CURRENT_STREAK] ?: 0,
            lastActiveDate = prefs[LAST_ACTIVE_DATE] ?: 0L
        )
    }

    // Save counter state (count + zikr)
    suspend fun saveCounterState(state: CounterState) {
        context.dataStore.edit { prefs ->
            prefs[CURRENT_ZIKR_ID] = state.currentZikrId
            prefs[COUNT] = state.count
            prefs[LAST_BACKUP_DATE] = System.currentTimeMillis()
        }
    }

    // Save goal target
    suspend fun saveGoalTarget(target: Int) {
        context.dataStore.edit { prefs ->
            prefs[GOAL_TARGET] = target
        }
    }

    // Save streak info
    suspend fun saveStreak(streak: Streak) {
        context.dataStore.edit { prefs ->
            prefs[CURRENT_STREAK] = streak.currentStreak
            prefs[LAST_ACTIVE_DATE] = streak.lastActiveDate
        }
    }

    // Check if this is the first launch
    suspend fun isFirstLaunch(): Boolean {
        return context.dataStore.data.map { prefs ->
            prefs[APP_FIRST_LAUNCH] ?: true
        }.map { isFirst ->
            if (isFirst) {
                context.dataStore.edit { prefs ->
                    prefs[APP_FIRST_LAUNCH] = false
                }
            }
            isFirst
        }.let { flow ->
            // Get the first value from the flow
            var result = true
            flow.collect { value ->
                result = value
            }
            result
        }
    }

    // Get all data for backup/debug
    suspend fun getAllData(): Map<String, Any> {
        val prefs = context.dataStore.data.map { it }.let { flow ->
            var result: Preferences? = null
            flow.collect { value ->
                result = value
            }
            result
        }

        return mutableMapOf<String, Any>().apply {
            prefs?.let {
                put("zikr_id", it[CURRENT_ZIKR_ID] ?: "subhanallah")
                put("count", it[COUNT] ?: 0)
                put("goal", it[GOAL_TARGET] ?: 100)
                put("streak", it[CURRENT_STREAK] ?: 0)
                put("last_active", it[LAST_ACTIVE_DATE] ?: 0L)
            }
        }
    }

    // Clear all data (for reset)
    suspend fun clearAllData() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
