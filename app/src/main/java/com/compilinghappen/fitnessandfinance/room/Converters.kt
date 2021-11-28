package com.compilinghappen.fitnessandfinance.room

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.util.*

class Converters {
    @TypeConverter
    fun fromBigDecimal(value: BigDecimal?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toBigDecimal(value: String?): BigDecimal? {
        return BigDecimal(value)
    }

    @TypeConverter
    fun fromDate(value: Date?): Long? {
        return value?.time
    }

    @TypeConverter
    fun toDate(value: Long?): Date? {
        if (value != null) {
            return Date(value)
        }
        return null
    }
}