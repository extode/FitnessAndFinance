package com.compilinghappen.fitnessandfinance.fitness

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.compilinghappen.fitnessandfinance.databinding.FragmentFitnessDataDialogBinding

class FitnessDataDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFitnessDataDialogBinding.inflate(inflater, container, false)



        return binding.root
    }
}