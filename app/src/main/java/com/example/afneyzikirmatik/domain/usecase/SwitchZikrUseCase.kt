package com.example.afneyzikirmatik.domain.usecase

import com.example.afneyzikirmatik.data.repository.CounterRepository
import com.example.afneyzikirmatik.domain.model.CounterState

class SwitchZikrUseCase(private val counterRepository: CounterRepository) {

    suspend operator fun invoke(newZikrId: String): CounterState {
        val newState = CounterState(currentZikrId = newZikrId, count = 0)
        counterRepository.saveCounterState(newState)
        return newState
    }
}
