package com.example.afneyzikirmatik.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.afneyzikirmatik.domain.model.Zikr

/**
 * Room entity for Zikr storage
 * Allows users to create custom zikrs
 */
@Entity(tableName = "zikrs")
data class ZikrEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val arabic: String,
    val translation: String,
    val english: String,
    val isCustom: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
) {
    fun toZikr(): Zikr {
        return Zikr(
            id = id,
            arabic = arabic,
            translation = translation,
            english = english
        )
    }

    companion object {
        fun fromZikr(zikr: Zikr, isCustom: Boolean = false): ZikrEntity {
            return ZikrEntity(
                id = zikr.id,
                arabic = zikr.arabic,
                translation = zikr.translation,
                english = zikr.english,
                isCustom = isCustom
            )
        }

        // Default predefined zikrs
        fun getDefaultZikrs(): List<ZikrEntity> = listOf(
            ZikrEntity(
                id = "subhanallah",
                arabic = "سُبْحَانَ اللَّهِ",
                translation = "Subhanallah",
                english = "Glory be to Allah"
            ),
            ZikrEntity(
                id = "alhamdulillah",
                arabic = "الْحَمْدُ لِلَّهِ",
                translation = "Alhamdulillah",
                english = "Praise be to Allah"
            ),
            ZikrEntity(
                id = "allahuakbar",
                arabic = "اللَّهُ أَكْبَرُ",
                translation = "Allahu Akbar",
                english = "Allah is the Greatest"
            )
        )
    }
}

