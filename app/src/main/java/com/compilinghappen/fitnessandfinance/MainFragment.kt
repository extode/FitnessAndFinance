package com.compilinghappen.fitnessandfinance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import com.compilinghappen.fitnessandfinance.finance.FinanceFragment
import com.compilinghappen.fitnessandfinance.fitness.FitnessFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainFragment : Fragment() {
    private val financeFragment: FinanceFragment by lazy {
        FinanceFragment()
    }

    private val fitnessFragment: FitnessFragment by lazy {
        FitnessFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val bottomNavBar = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        return view
    }

    private fun setFinanceFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fcw, financeFragment)
            .setReorderingAllowed(true)
            .commit()
    }

    private fun setFitnessFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fcw, fitnessFragment)
            .setReorderingAllowed(true)
            .commit()
    }
}