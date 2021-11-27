package com.compilinghappen.fitnessandfinance.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(categoryDb: CategoryDb)

    @Update(onConflict = REPLACE)
    suspend fun update(categoryDb: CategoryDb)

    @Query("select * from categories where pk_categoryId = :categoryId")
    suspend fun get(categoryId: Long): CategoryDb?

    @Transaction
    @Query("select * from categories where pk_categoryId = :categoryId")
    suspend fun getWithReceipts(categoryId: Long): CategoryWithReceiptsDb?

    @Query("select * from categories")
    fun getAll(): LiveData<List<CategoryDb>>

    @Query("select * from categories")
    fun getAllNoLiveData(): List<CategoryDb>
}
