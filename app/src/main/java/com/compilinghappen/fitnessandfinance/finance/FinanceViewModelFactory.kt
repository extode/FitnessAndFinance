package com.compilinghappen.fitnessandfinance.finance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.compilinghappen.fitnessandfinance.room.CategoryDao
import com.compilinghappen.fitnessandfinance.room.ReceiptDao

class FinanceViewModelFactory(
    private val categoryDao: CategoryDao,
    private val receiptDao: ReceiptDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinanceViewModel::class.java)) {
            return FinanceViewModel(categoryDao, receiptDao) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}