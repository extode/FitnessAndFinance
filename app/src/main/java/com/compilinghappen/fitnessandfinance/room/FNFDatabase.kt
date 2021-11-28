package com.compilinghappen.fitnessandfinance.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ReceiptDb::class, CategoryDb::class, FitnessData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FNFDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: FNFDatabase? = null

        fun getInstance(context: Context): FNFDatabase = synchronized(this) {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext, FNFDatabase::class.java, "fnf_database"
                ).build()
                INSTANCE = instance
            }
            return instance
        }
    }


    abstract val categoryDao: CategoryDao
    abstract val receiptDao: ReceiptDao
    abstract val fitnessDataDao: FitnessDataDao
}

