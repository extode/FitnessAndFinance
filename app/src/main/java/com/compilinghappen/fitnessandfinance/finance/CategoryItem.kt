package com.compilinghappen.fitnessandfinance.finance

import java.math.BigDecimal

data class CategoryItem(
    val id: Long,
    val name: String,
    val cash: BigDecimal,
    val limit: BigDecimal? = null
) {
    fun isCounter(): Boolean {
        return limit != null && limit == BigDecimal.ZERO
    }

    fun isLimited() = !isCounter()
}