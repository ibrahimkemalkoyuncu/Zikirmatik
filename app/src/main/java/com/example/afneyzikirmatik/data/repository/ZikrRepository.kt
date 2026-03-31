package com.example.afneyzikirmatik.data.repository

import com.example.afneyzikirmatik.domain.model.Zikr
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ZikrRepository {

    private val predefinedZikrs = listOf(
        Zikr(
            id = "subhanallah",
            arabic = "سُبْحَانَ اللَّهِ",
            translation = "Subhanallah",
            english = "Glory be to Allah"
        ),
        Zikr(
            id = "alhamdulillah",
            arabic = "الْحَمْدُ لِلَّهِ",
            translation = "Alhamdulillah",
            english = "Praise be to Allah"
        ),
        Zikr(
            id = "allahuakbar",
            arabic = "اللَّهُ أَكْبَرُ",
            translation = "Allahu Akbar",
            english = "Allah is the Greatest"
        )
    )

    fun getAllZikrs(): Flow<List<Zikr>> = flowOf(predefinedZikrs)

    fun getZikrById(id: String): Zikr? = predefinedZikrs.find { it.id == id }
}
