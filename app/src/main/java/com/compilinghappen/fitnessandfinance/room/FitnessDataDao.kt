package com.compilinghappen.fitnessandfinance.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FitnessDataDao {
    @Insert
    suspend fun insert(fitnessData: FitnessData)

    @Query("select * from fitness order by date desc")
    fun getAll(): LiveData<List<FitnessData>>

    @Query("delete from fitness")
    suspend fun clear()
}