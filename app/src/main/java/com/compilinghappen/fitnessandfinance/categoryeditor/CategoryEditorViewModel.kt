package com.compilinghappen.fitnessandfinance.categoryeditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.compilinghappen.fitnessandfinance.room.CategoryDao
import com.compilinghappen.fitnessandfinance.room.CategoryDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class CategoryEditorViewModel(private val categoryDao: CategoryDao) : ViewModel() {
    private val _coroutineScope = CoroutineScope(Dispatchers.Main)

    val categoriesList = categoryDao.getAll()

    private val _createNewCategoryEvent = MutableLiveData<Boolean>()
    val createNewCategoryEvent: LiveData<Boolean>
        get() = _createNewCategoryEvent

    fun addNewCategory() {
        _createNewCategoryEvent.value = true
    }

    fun addNewCategory(categoryDb: CategoryDb) {
        _coroutineScope.launch {
            categoryDao.insert(categoryDb)
        }
    }

    fun createNewCategoryEventHandled() {
        _createNewCategoryEvent.value = false
    }

    fun handleArgs(categoryName: String?, categoryFilters: Array<String>?, limit: String?) {
        if (categoryName == null || categoryName == "null")
            return

        addNewCategory(
            CategoryDb(
                0L,
                categoryName,
                categoryFilters!!.joinToString(separator = "&&") { it },
                BigDecimal(limit ?: "0")
            )
        )
    }
}