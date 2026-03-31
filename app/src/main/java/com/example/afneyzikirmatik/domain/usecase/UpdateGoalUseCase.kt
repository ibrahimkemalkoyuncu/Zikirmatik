package com.example.afneyzikirmatik.domain.usecase

import com.example.afneyzikirmatik.data.repository.CounterRepository

class UpdateGoalUseCase(private val counterRepository: CounterRepository) {

    suspend operator fun invoke(target: Int) {
        counterRepository.saveGoalTarget(target)
    }
}
