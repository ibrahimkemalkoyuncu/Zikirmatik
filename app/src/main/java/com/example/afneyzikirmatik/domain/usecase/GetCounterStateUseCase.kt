package com.example.afneyzikirmatik.domain.usecase

import com.example.afneyzikirmatik.data.repository.CounterRepository
import com.example.afneyzikirmatik.domain.model.CounterState
import kotlinx.coroutines.flow.Flow

class GetCounterStateUseCase(private val counterRepository: CounterRepository) {

    operator fun invoke(): Flow<CounterState> = counterRepository.getCounterState()
}
