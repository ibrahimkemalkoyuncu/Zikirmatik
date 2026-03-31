package com.example.afneyzikirmatik.data.repository

import com.example.afneyzikirmatik.data.local.ZikrDao
import com.example.afneyzikirmatik.data.local.ZikrEntity
import com.example.afneyzikirmatik.domain.model.Zikr
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository for Zikr data management
 * Combines predefined and custom zikrs
 */
class ZikrRepository(private val zikrDao: ZikrDao? = null) {

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

    /**
     * Get all zikrs (predefined + custom from Room)
     */
    fun getAllZikrs(): Flow<List<Zikr>> {
        return if (zikrDao != null) {
            zikrDao.getAllZikrsFlow().map { entities ->
                entities.map { it.toZikr() }
            }
        } else {
            kotlinx.coroutines.flow.flowOf(predefinedZikrs)
        }
    }

    /**
     * Get zikr by ID
     */
    fun getZikrById(id: String): Zikr? {
        return predefinedZikrs.find { it.id == id }
    }

    /**
     * Create custom zikr
     */
    suspend fun createCustomZikr(zikr: Zikr) {
        zikrDao?.let { dao ->
            val entity = ZikrEntity.fromZikr(zikr, isCustom = true)
            dao.insertZikr(entity)
        }
    }

    /**
     * Delete custom zikr
     */
    suspend fun deleteCustomZikr(zikrId: String) {
        zikrDao?.let { dao ->
            dao.deleteZikr(zikrId)
        }
    }

    /**
     * Initialize database with predefined zikrs (on first launch)
     */
    suspend fun initializePredefinedZikrs() {
        zikrDao?.let { dao ->
            val entities = ZikrEntity.getDefaultZikrs()
            dao.insertZikrs(entities)
        }
    }
}
