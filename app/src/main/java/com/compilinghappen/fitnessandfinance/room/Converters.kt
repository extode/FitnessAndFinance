package com.compilinghappen.fitnessandfinance.room

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {
    @TypeConverter
    fun fromBigDecimal(value: BigDecimal?): String? {
        return value.toString()
    }

    @TypeConverter
    fun toBigDecimal(value: String?): BigDecimal? {
        return BigDecimal(value)
    }
}