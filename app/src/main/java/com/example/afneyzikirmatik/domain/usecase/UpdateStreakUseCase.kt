package com.example.afneyzikirmatik.domain.usecase

import com.example.afneyzikirmatik.data.repository.CounterRepository
import com.example.afneyzikirmatik.domain.model.Streak

class UpdateStreakUseCase(private val counterRepository: CounterRepository) {

    suspend operator fun invoke(streak: Streak) {
        counterRepository.saveStreak(streak)
    }
}
