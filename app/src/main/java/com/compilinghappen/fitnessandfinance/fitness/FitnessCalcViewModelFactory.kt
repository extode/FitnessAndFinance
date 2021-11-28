package com.compilinghappen.fitnessandfinance.fitness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.compilinghappen.fitnessandfinance.room.FitnessDataDao

class FitnessCalcViewModelFactory(private val fitnessDataDao: FitnessDataDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FitnessCalcViewModel::class.java)) {
            return FitnessCalcViewModel(fitnessDataDao) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}