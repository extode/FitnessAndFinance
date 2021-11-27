package com.compilinghappen.fitnessandfinance.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ReceiptDao {

    @Insert
    suspend fun insert(receiptDb: ReceiptDb)

    @Query("delete from receipts where fk_categoryId = :categoryId")
    suspend fun clearByCategoryId(categoryId: Long)

    @Query("select * from receipts where fk_categoryId = :categoryId")
    suspend fun getByCategoryId(categoryId: Long): List<ReceiptDb>

    @Query("delete from receipts")
    suspend fun clear()
}