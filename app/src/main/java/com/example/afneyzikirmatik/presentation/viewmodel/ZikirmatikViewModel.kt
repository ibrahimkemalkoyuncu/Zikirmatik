package com.example.afneyzikirmatik.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.afneyzikirmatik.data.local.HapticManager
import com.example.afneyzikirmatik.data.local.PreferencesManager
import com.example.afneyzikirmatik.data.repository.CounterRepository
import com.example.afneyzikirmatik.data.repository.ZikrRepository
import com.example.afneyzikirmatik.domain.model.CounterState
import com.example.afneyzikirmatik.domain.model.Goal
import com.example.afneyzikirmatik.domain.model.Streak
import com.example.afneyzikirmatik.domain.model.Zikr
import com.example.afneyzikirmatik.domain.usecase.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar

class ZikirmatikViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesManager = PreferencesManager(application)
    private val counterRepository = CounterRepository(preferencesManager)
    private val zikrRepository = ZikrRepository()

    private val getZikrsUseCase = GetZikrsUseCase(zikrRepository)
    private val getCounterStateUseCase = GetCounterStateUseCase(counterRepository)
    private val incrementCounterUseCase = IncrementCounterUseCase(counterRepository)
    private val switchZikrUseCase = SwitchZikrUseCase(counterRepository)
    private val getGoalUseCase = GetGoalUseCase(counterRepository)
    private val updateGoalUseCase = UpdateGoalUseCase(counterRepository)
    private val getStreakUseCase = GetStreakUseCase(counterRepository)
    private val updateStreakUseCase = UpdateStreakUseCase(counterRepository)
    private val hapticManager = HapticManager(application)

    private val _counterState = MutableStateFlow(CounterState("subhanallah", 0))
    val counterState: StateFlow<CounterState> = _counterState.asStateFlow()

    private val _zikrs = MutableStateFlow<List<Zikr>>(emptyList())
    val zikrs: StateFlow<List<Zikr>> = _zikrs.asStateFlow()

    private val _goal = MutableStateFlow(Goal(100, 0))
    val goal: StateFlow<Goal> = _goal.asStateFlow()

    private val _streak = MutableStateFlow(Streak(0, 0L))
    val streak: StateFlow<Streak> = _streak.asStateFlow()

    private val _currentZikr = MutableStateFlow<Zikr?>(null)
    val currentZikr: StateFlow<Zikr?> = _currentZikr.asStateFlow()

    private val _milestone = MutableStateFlow<String?>(null)
    val milestone: StateFlow<String?> = _milestone.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getZikrsUseCase().collect { zikrs ->
                _zikrs.value = zikrs
                updateCurrentZikr()
            }
        }
        viewModelScope.launch {
            getCounterStateUseCase().collect { state ->
                _counterState.value = state
                updateCurrentZikr()
            }
        }
        viewModelScope.launch {
            getGoalUseCase().collect { goal ->
                _goal.value = goal
            }
        }
        viewModelScope.launch {
            getStreakUseCase().collect { streak ->
                _streak.value = streak
            }
        }
    }

    private fun updateCurrentZikr() {
        _currentZikr.value = _zikrs.value.find { it.id == _counterState.value.currentZikrId }
    }

    fun incrementCounter() {
        hapticManager.vibrate()
        viewModelScope.launch {
            val newState = incrementCounterUseCase(_counterState.value)
            _counterState.value = newState
            updateStreakIfNeeded()
            checkMilestone()
        }
    }

    fun switchZikr(zikrId: String) {
        viewModelScope.launch {
            val newState = switchZikrUseCase(zikrId)
            _counterState.value = newState
            updateCurrentZikr()
        }
    }

    fun updateGoal(target: Int) {
        viewModelScope.launch {
            updateGoalUseCase(target)
            // Goal flow will update automatically
        }
    }

    private fun updateStreakIfNeeded() {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val lastActive = _streak.value.lastActiveDate
        val currentStreak = _streak.value.currentStreak

        val newStreak = when {
            lastActive == 0L -> Streak(1, today) // First time
            lastActive == today -> _streak.value // Already counted today
            isConsecutiveDay(lastActive, today) -> Streak(currentStreak + 1, today)
            else -> Streak(1, today) // Reset streak
        }

        if (newStreak != _streak.value) {
            viewModelScope.launch {
                updateStreakUseCase(newStreak)
            }
        }
    }

    private fun checkMilestone() {
        if (_counterState.value.count % 33 == 0) {
            _milestone.value = "You've reached ${_counterState.value.count} counts!"
        } else {
            _milestone.value = null
        }
    }

    private fun isConsecutiveDay(last: Long, today: Long): Boolean {
        val diff = (today - last) / (1000 * 60 * 60 * 24)
        return diff == 1L
    }
}
