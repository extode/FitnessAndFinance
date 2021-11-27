package com.compilinghappen.fitnessandfinance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        view.findViewById<CardView>(R.id.finance_card).setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToFinanceFragment()
            )
        }

        return view
    }
}