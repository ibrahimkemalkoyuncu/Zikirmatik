package com.example.afneyzikirmatik.domain.usecase

import com.example.afneyzikirmatik.data.repository.CounterRepository
import com.example.afneyzikirmatik.domain.model.Streak
import kotlinx.coroutines.flow.Flow

class GetStreakUseCase(private val counterRepository: CounterRepository) {

    operator fun invoke(): Flow<Streak> = counterRepository.getStreak()
}
