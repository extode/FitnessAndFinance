package com.compilinghappen.fitnessandfinance.fitness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.compilinghappen.fitnessandfinance.pedometer.PedometerService
import com.compilinghappen.fitnessandfinance.room.FitnessData
import com.compilinghappen.fitnessandfinance.room.FitnessDataDao
import kotlinx.coroutines.*

class FitnessCalcViewModel(private val fitnessDao: FitnessDataDao) : ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val fitnessList = fitnessDao.getAll()

    private var _stepsCount = MutableLiveData<Int>()
    val stepsCount: LiveData<Int>
        get() = _stepsCount

    init {
        coroutineScope.launch {
            while (isActive) {
                _stepsCount.value = PedometerService.NUMBER_OF_STEPS
                delay(1000)
            }
        }
    }

    fun addNew(fitnessData: FitnessData) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                fitnessDao.insert(fitnessData)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}