package com.compilinghappen.fitnessandfinance.fitness

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.compilinghappen.fitnessandfinance.R
import com.compilinghappen.fitnessandfinance.databinding.FragmentFitnessBinding
import com.compilinghappen.fitnessandfinance.pedometer.PedometerService
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

class FitnessFragment : Fragment() {

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var binding: FragmentFitnessBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFitnessBinding.inflate(inflater, container, false);

        binding.calcCardView.setOnClickListener {
            findNavController().navigate(
                FitnessFragmentDirections.actionFitnessFragmentToFitnessCalcFragment()
            )
        }

        coroutineScope = CoroutineScope(Dispatchers.Main)

        coroutineScope.launch {
            while (isActive) {
                binding.stepsValueTextView.text = PedometerService.NUMBER_OF_STEPS.toString()
                binding.caloriesValueTextView.text = ("%.2f".format(binding.stepsValueTextView.text.toString().toInt() * 0.05))
                delay(1000)
            }
        }

        binding.trainingCard.setOnClickListener {
            findNavController().navigate(
                FitnessFragmentDirections.actionFitnessFragmentToWorkoutListFragment()
            )
        }

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.cancel()
    }
}