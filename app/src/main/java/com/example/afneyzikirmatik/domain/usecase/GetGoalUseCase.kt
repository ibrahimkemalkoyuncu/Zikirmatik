package com.example.afneyzikirmatik.domain.usecase

import com.example.afneyzikirmatik.data.repository.CounterRepository
import com.example.afneyzikirmatik.domain.model.Goal
import kotlinx.coroutines.flow.Flow

class GetGoalUseCase(private val counterRepository: CounterRepository) {

    operator fun invoke(): Flow<Goal> = counterRepository.getGoal()
}
