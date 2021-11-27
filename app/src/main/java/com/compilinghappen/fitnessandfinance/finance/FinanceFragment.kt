package com.compilinghappen.fitnessandfinance.finance

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.compilinghappen.fitnessandfinance.R
import com.compilinghappen.fitnessandfinance.databinding.FragmentFinanceBinding
import com.compilinghappen.fitnessandfinance.room.FNFDatabase

class FinanceFragment : Fragment() {
    private val viewModel: FinanceViewModel by viewModels {
        FinanceViewModelFactory(
            FNFDatabase.getInstance(requireContext()).categoryDao,
            FNFDatabase.getInstance(requireContext()).receiptDao
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.init()
        val binding = FragmentFinanceBinding.inflate(inflater, container, false)

        val adapter = FinanceAdapter()
        binding.financeRecyclerview.adapter = adapter

        viewModel.categoryItems.observe(viewLifecycleOwner) {
            adapter.applyData(it)
        }

        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_finance, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.financeFragment -> {
                findNavController().navigate(
                    FinanceFragmentDirections.actionFinanceFragmentToCategoryEditorFragment(null, null, null)
                )
            }
            R.id.action_enable_notifications -> {
                val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                startActivity(intent)
            }
            R.id.action_clear_receipts -> {
                viewModel.clearReceipts()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}