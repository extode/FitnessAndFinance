package com.compilinghappen.fitnessandfinance

import com.compilinghappen.fitnessandfinance.notification.NotificationInfo

interface Filter {
    fun isMatch(notification: NotificationInfo): Boolean
}
