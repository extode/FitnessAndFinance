package com.compilinghappen.fitnessandfinance

import com.compilinghappen.fitnessandfinance.notification.NotificationInfo
import com.compilinghappen.fitnessandfinance.room.CategoryDb

class StringListFilter(private val list: List<String>) : Filter {
    override fun isMatch(notification: NotificationInfo): Boolean {
        val msg = notification.appName + "\n" + notification.title + "\n" + notification.text
        for (filter in list) {
            if (!msg.contains(filter, ignoreCase = true)) {
                return false
            }
        }
        return true
    }
}

fun CategoryDb.createFilter(): Filter {
    return StringListFilter(parseFilters())
}