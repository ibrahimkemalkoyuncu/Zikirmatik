package com.example.afneyzikirmatik.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Main Room Database
 * Singleton pattern for single database instance
 */
@Database(
    entities = [ZikrEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ZikirmatikDatabase : RoomDatabase() {

    abstract fun zikrDao(): ZikrDao

    companion object {
        @Volatile
        private var INSTANCE: ZikirmatikDatabase? = null

        fun getDatabase(context: Context): ZikirmatikDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ZikirmatikDatabase::class.java,
                    "zikirmatik_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}

