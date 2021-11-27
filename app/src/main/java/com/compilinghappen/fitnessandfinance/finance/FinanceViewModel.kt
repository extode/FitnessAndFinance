package com.compilinghappen.fitnessandfinance.finance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.compilinghappen.fitnessandfinance.NotificationIndicator
import com.compilinghappen.fitnessandfinance.room.CategoryDao
import com.compilinghappen.fitnessandfinance.room.ReceiptDao
import kotlinx.coroutines.*

class FinanceViewModel(private val categoryDao: CategoryDao, private val receiptDao: ReceiptDao) : ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val _categoryItems = MutableLiveData<List<CategoryItem>>()
    val categoryItems: LiveData<List<CategoryItem>>
        get() = _categoryItems

    fun init() {
        _load()

        coroutineScope.launch {
            while (isActive) {
                if (NotificationIndicator.notificationPosted) {
                    _load()
                    NotificationIndicator.notificationPosted = false
                }
                delay(2000)
            }
        }
    }

    private fun _load() {
        coroutineScope.launch {
            val items = _loadCategoryItems()
            _categoryItems.value = items
        }
    }

    private suspend fun _loadCategoryItems(): List<CategoryItem> {
        val items = ArrayList<CategoryItem>()
        withContext(Dispatchers.IO) {
            val dbCategories = categoryDao.getAllNoLiveData()
            for (dbCategory in dbCategories) {
                val receipts = receiptDao.getByCategoryId(dbCategory.pk_categoryId)
                val sum = receipts.sumOf { it.cost }
                items.add(
                    CategoryItem(
                        dbCategory.pk_categoryId,
                        dbCategory.name,
                        sum,
                        dbCategory.limit
                    )
                )
            }
        }
        return items
    }

    fun clearReceipts() {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                receiptDao.clear()
            }

            _load()
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}