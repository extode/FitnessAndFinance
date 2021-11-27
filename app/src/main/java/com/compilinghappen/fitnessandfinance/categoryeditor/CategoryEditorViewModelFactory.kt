package com.compilinghappen.fitnessandfinance.categoryeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.compilinghappen.fitnessandfinance.room.CategoryDao
import java.lang.IllegalArgumentException

class CategoryEditorViewModelFactory(private val categoryDao: CategoryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryEditorViewModel::class.java)) {
            return CategoryEditorViewModel(categoryDao) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}