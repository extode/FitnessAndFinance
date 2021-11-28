package com.compilinghappen.fitnessandfinance.fitness

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.compilinghappen.fitnessandfinance.R
import com.compilinghappen.fitnessandfinance.databinding.FragmentFitnessBinding

class FitnessFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFitnessBinding.inflate(inflater, container, false);

        binding.calcCardView.setOnClickListener {
            findNavController().navigate(
                FitnessFragmentDirections.actionFitnessFragmentToFitnessCalcFragment()
            )
        }

        return binding.root
    }

}