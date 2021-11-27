package com.compilinghappen.fitnessandfinance

import com.compilinghappen.fitnessandfinance.notification.NotificationInfo
import com.compilinghappen.fitnessandfinance.room.CategoryDb

class StringListFilter(private val list: List<String>) : Filter {
    override fun isMatch(notification: NotificationInfo): Boolean {
        for (filter in list) {
            if (notification.text.contains(filter, ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}

fun CategoryDb.createFilter(): Filter {
    return StringListFilter(parseFilters())
}