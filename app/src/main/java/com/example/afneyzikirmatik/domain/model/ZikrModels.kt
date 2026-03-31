package com.example.afneyzikirmatik.domain.model

data class Zikr(
    val id: String,
    val arabic: String,
    val translation: String,
    val english: String
)

data class CounterState(
    val currentZikrId: String,
    val count: Int
)

data class Goal(
    val target: Int,
    val currentProgress: Int
)

data class Streak(
    val currentStreak: Int,
    val lastActiveDate: Long // timestamp
)
