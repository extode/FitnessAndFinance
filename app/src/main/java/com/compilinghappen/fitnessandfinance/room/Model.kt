package com.compilinghappen.fitnessandfinance.room

import androidx.room.*
import java.math.BigDecimal

@Entity(tableName = "receipts")
data class ReceiptDb(
    @PrimaryKey(autoGenerate = true)
    var pk_receiptId: Long,

    var fk_categoryId: Long,

    @ColumnInfo(name = "cost")
    var cost: BigDecimal
)


@Entity(tableName = "categories")
class CategoryDb(
    @PrimaryKey(autoGenerate = true)
    var pk_categoryId: Long,

    @ColumnInfo(name = "category_name")
    var name: String,

    @ColumnInfo(name = "filters")
    var filters: String, // array of strings, separated by &&

    @ColumnInfo(name = "limit")
    var limit: BigDecimal,
) {
    fun parseFilters() = filters.split("&&")
}


data class CategoryWithReceiptsDb(
    @Embedded val category: CategoryDb,

    @Relation(
        parentColumn = "pk_categoryId",
        entityColumn = "fk_categoryId"
    )
    val receipts: List<ReceiptDb>
)