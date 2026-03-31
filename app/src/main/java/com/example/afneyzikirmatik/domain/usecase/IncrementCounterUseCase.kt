package com.example.afneyzikirmatik.domain.usecase

import com.example.afneyzikirmatik.data.repository.CounterRepository
import com.example.afneyzikirmatik.domain.model.CounterState

class IncrementCounterUseCase(private val counterRepository: CounterRepository) {

    suspend operator fun invoke(currentState: CounterState): CounterState {
        val newState = currentState.copy(count = currentState.count + 1)
        counterRepository.saveCounterState(newState)
        return newState
    }

    suspend fun saveState(state: CounterState) {
        counterRepository.saveCounterState(state)
    }
}
