package com.compilinghappen.fitnessandfinance.notification

import android.app.Notification
import android.content.pm.ApplicationInfo
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.compilinghappen.fitnessandfinance.NotificationIndicator
import com.compilinghappen.fitnessandfinance.createFilter
import com.compilinghappen.fitnessandfinance.room.FNFDatabase
import com.compilinghappen.fitnessandfinance.room.ReceiptDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class NotificationListener : NotificationListenerService() {
    private var prevNotification = NotificationInfo()

    private lateinit var database: FNFDatabase
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        database = FNFDatabase.getInstance(this)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        if (sbn == null)
            return

        val bundle = sbn.notification.extras

        val appInfo = bundle.get("android.appInfo") as ApplicationInfo
        val appName = packageManager.getApplicationLabel(appInfo).toString()
        val title = bundle.get(Notification.EXTRA_TITLE)?.toString() ?: return

        var text: String? = bundle.get(Notification.EXTRA_TEXT)?.toString()
        var prefix = Notification.EXTRA_TEXT
        if (text == null) {
            prefix = Notification.EXTRA_TEXT_LINES
            text = bundle.get(Notification.EXTRA_TEXT_LINES)?.toString()
        }
        if (text == null) {
            prefix = "unknown"
            text = bundle.toString()
        }

        val notification = NotificationInfo(
            appName = appName,
            title = title,
            text = "$prefix $text",
            date = sbn.notification.`when`
        )

        if (prevNotification != notification) {
            prevNotification = notification
            coroutineScope.launch {
                onNotificationArrived(notification)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    private suspend fun onNotificationArrived(notification: NotificationInfo) {
        var lastNumber = readNumber(notification.text, 0) ?: return
        lastNumber = lastNumber.replace(',', '.')

        var isHandled = false
        val categories = database.categoryDao.getAllNoLiveData()
        for (category in categories) {
            val filter = category.createFilter()
            if (filter.isMatch(notification)) {
                database.receiptDao.insert(
                    ReceiptDb(
                        0L,
                        category.pk_categoryId,
                        lastNumber.toBigDecimal()
                    )
                )
                isHandled = true
            }
        }

        if (isHandled) {
            NotificationIndicator.notificationPosted = true
        }
    }

    private fun getLastNumber(string: String): String? {
        var i = string.length - 1
        var numberEnd = string.length
        var inNumber = false
        while (i >= 0) {
            val c = string[i]
            if (c.isDigit()) {
                numberEnd = i + 1
                inNumber = true
            } else if (inNumber) {
                return string.substring(i, numberEnd)
            }
            ++i
        }

        if (inNumber) {
            return string.substring(0, numberEnd)
        }
        return null
    }

    private fun readNumber(string: String, start: Int): String? {
        var i = start
        var numberStart = string.length
        var inNumber = false
        var pointOccurs = false
        while (i < string.length) {
            val c = string[i]
            if (c.isDigit() && !inNumber) {
                numberStart = i
                inNumber = true
            }
            else if ((c == '.' || c == ',') && !pointOccurs && inNumber) {
                pointOccurs = true
            }
            else if (!c.isDigit() && inNumber) {
                return string.substring(numberStart, i)
            }
            ++i
        }

        if (inNumber) {
            return string.substring(numberStart)
        }
        return null
    }
}