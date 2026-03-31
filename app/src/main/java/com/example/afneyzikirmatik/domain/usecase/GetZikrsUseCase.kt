package com.example.afneyzikirmatik.domain.usecase

import com.example.afneyzikirmatik.data.repository.ZikrRepository
import com.example.afneyzikirmatik.domain.model.Zikr
import kotlinx.coroutines.flow.Flow

class GetZikrsUseCase(private val zikrRepository: ZikrRepository) {

    operator fun invoke(): Flow<List<Zikr>> = zikrRepository.getAllZikrs()
}
