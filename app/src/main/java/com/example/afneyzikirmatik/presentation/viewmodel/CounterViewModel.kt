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

// UI State for Counter
data class CounterUiState(
    val count: Int = 0,
    val currentZikrId: String = "subhanallah",
    val currentZikr: Zikr? = null,
    val goal: Goal = Goal(100, 0),
    val streak: Streak = Streak(0, 0L),
    val isMilestone: Boolean = false,
    val isLoading: Boolean = true
)

class CounterViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesManager = PreferencesManager(application)
    private val counterRepository = CounterRepository(preferencesManager)
    private val zikrRepository = ZikrRepository()
    private val hapticManager = HapticManager(application)

    private val getZikrsUseCase = GetZikrsUseCase(zikrRepository)
    private val getCounterStateUseCase = GetCounterStateUseCase(counterRepository)
    private val incrementCounterUseCase = IncrementCounterUseCase(counterRepository)
    private val switchZikrUseCase = SwitchZikrUseCase(counterRepository)
    private val getGoalUseCase = GetGoalUseCase(counterRepository)
    private val updateGoalUseCase = UpdateGoalUseCase(counterRepository)
    private val getStreakUseCase = GetStreakUseCase(counterRepository)
    private val updateStreakUseCase = UpdateStreakUseCase(counterRepository)

    // State Flows
    private val _zikrs = MutableStateFlow<List<Zikr>>(emptyList())
    val zikrs: StateFlow<List<Zikr>> = _zikrs.asStateFlow()

    private val _counterState = MutableStateFlow(CounterState("subhanallah", 0))
    val counterState: StateFlow<CounterState> = _counterState.asStateFlow()

    private val _goal = MutableStateFlow(Goal(100, 0))
    val goal: StateFlow<Goal> = _goal.asStateFlow()

    private val _streak = MutableStateFlow(Streak(0, 0L))
    val streak: StateFlow<Streak> = _streak.asStateFlow()

    private val _currentZikr = MutableStateFlow<Zikr?>(null)
    val currentZikr: StateFlow<Zikr?> = _currentZikr.asStateFlow()

    private val _milestone = MutableStateFlow<String?>(null)
    val milestone: StateFlow<String?> = _milestone.asStateFlow()

    // Combo UI State
    val uiState: StateFlow<CounterUiState> = combine(
        _counterState,
        _currentZikr,
        _goal,
        _streak
    ) { state, zikr, goal, streak ->
        CounterUiState(
            count = state.count,
            currentZikrId = state.currentZikrId,
            currentZikr = zikr,
            goal = goal,
            streak = streak,
            isMilestone = state.count > 0 && state.count % 33 == 0,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CounterUiState(isLoading = true)
    )

    init {
        loadAllData()
        initializeFirstLaunch()
    }

    private fun initializeFirstLaunch() {
        viewModelScope.launch {
            if (counterRepository.isFirstLaunch()) {
                // Set default values on first launch
                updateGoalUseCase(100)
            }
        }
    }

    private fun loadAllData() {
        viewModelScope.launch {
            // Load zikrs
            getZikrsUseCase().collect { zikrs ->
                _zikrs.value = zikrs
                updateCurrentZikr()
            }
        }

        viewModelScope.launch {
            // Load counter state
            getCounterStateUseCase().collect { state ->
                _counterState.value = state
                updateCurrentZikr()
            }
        }

        viewModelScope.launch {
            // Load goal
            getGoalUseCase().collect { goal ->
                _goal.value = goal
            }
        }

        viewModelScope.launch {
            // Load streak
            getStreakUseCase().collect { streak ->
                _streak.value = streak
            }
        }
    }

    private fun updateCurrentZikr() {
        _currentZikr.value = _zikrs.value.find { it.id == _counterState.value.currentZikrId }
    }

    // Sayaç artırma (tıklama ve tutma)
    fun incrementCounter() {
        hapticManager.vibrate()
        viewModelScope.launch {
            val newState = incrementCounterUseCase(_counterState.value)
            _counterState.value = newState
            
            // Milestone kontrolü
            checkMilestone(newState.count)
            
            // Streak güncelle
            updateStreakIfNeeded()
        }
    }

    // Zikir değiştirme
    fun switchZikr(zikrId: String) {
        viewModelScope.launch {
            val newState = switchZikrUseCase(zikrId)
            _counterState.value = newState
            updateCurrentZikr()
            hapticManager.vibrate()
        }
    }

    // Günlük hedef güncelleme
    fun updateGoal(target: Int) {
        viewModelScope.launch {
            updateGoalUseCase(target)
        }
    }

    // Milestone kontrolü (33'ün katlarında)
    private fun checkMilestone(count: Int) {
        if (count > 0 && count % 33 == 0) {
            _milestone.value = "🌟 ${count} sayısına ulaştınız! Harika! 🌟"
            
            // 2 saniye sonra sıfırla
            viewModelScope.launch {
                kotlinx.coroutines.delay(2000)
                _milestone.value = null
            }
        }
    }

    // Streak güncelle (gün değiştiğinde)
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
            lastActive == 0L -> Streak(1, today)
            lastActive == today -> _streak.value
            isConsecutiveDay(lastActive, today) -> Streak(currentStreak + 1, today)
            else -> Streak(1, today)
        }

        if (newStreak != _streak.value) {
            viewModelScope.launch {
                updateStreakUseCase(newStreak)
            }
        }
    }

    private fun isConsecutiveDay(last: Long, today: Long): Boolean {
        val diff = (today - last) / (1000 * 60 * 60 * 24)
        return diff == 1L
    }

    // Sayaç sıfırla (yönetim için)
    fun resetCounter() {
        viewModelScope.launch {
            val resetState = CounterState(_counterState.value.currentZikrId, 0)
            incrementCounterUseCase.saveState(resetState)
            _counterState.value = resetState
            hapticManager.vibrate()
        }
    }
}
