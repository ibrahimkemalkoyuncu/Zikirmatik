package com.example.afneyzikirmatik.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Zikr operations
 */
@Dao
interface ZikrDao {

    // Get all zikrs
    @Query("SELECT * FROM zikrs WHERE isActive = 1 ORDER BY createdAt DESC")
    fun getAllZikrsFlow(): Flow<List<ZikrEntity>>

    // Get all zikrs (single query)
    @Query("SELECT * FROM zikrs WHERE isActive = 1 ORDER BY createdAt DESC")
    suspend fun getAllZikrs(): List<ZikrEntity>

    // Get zikr by ID
    @Query("SELECT * FROM zikrs WHERE id = :id")
    suspend fun getZikrById(id: String): ZikrEntity?

    // Get custom zikrs only
    @Query("SELECT * FROM zikrs WHERE isCustom = 1 AND isActive = 1 ORDER BY createdAt DESC")
    fun getCustomZikrsFlow(): Flow<List<ZikrEntity>>

    // Insert zikr
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZikr(zikr: ZikrEntity)

    // Insert multiple zikrs
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZikrs(zikrs: List<ZikrEntity>)

    // Update zikr
    @Update
    suspend fun updateZikr(zikr: ZikrEntity)

    // Soft delete (mark as inactive)
    @Query("UPDATE zikrs SET isActive = 0 WHERE id = :id")
    suspend fun deleteZikr(id: String)

    // Delete all custom zikrs
    @Query("DELETE FROM zikrs WHERE isCustom = 1")
    suspend fun deleteAllCustomZikrs()

    // Check if zikr exists
    @Query("SELECT EXISTS(SELECT 1 FROM zikrs WHERE id = :id AND isActive = 1)")
    suspend fun zikrExists(id: String): Boolean
}

