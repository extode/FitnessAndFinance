package com.compilinghappen.fitnessandfinance.categoryeditor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.compilinghappen.fitnessandfinance.databinding.FragmentCategoryBuilderBinding
import java.math.BigDecimal

class CategoryBuilderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCategoryBuilderBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            val filters: List<String> = binding.keyWordsText.text.toString()
                .split(";")
                .map { it.trim() }

            val name = binding.categoryNameText.text.toString().trim()
            var limit: String? = binding.limitText.text.toString().trim()
            if (limit?.isEmpty() == true)
                limit = null
            if (name != "" && limit != "") {
                saveCategory(name, filters, limit)
            } else Toast.makeText(requireContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun saveCategory(title: String, filters: List<String>, limit: String?) {
        findNavController().navigate(
            CategoryBuilderFragmentDirections.actionCategoryBuilderFragmentToCategoryEditorFragment(
                title,
                filters.toTypedArray(),
                limit
            )
        )
    }
}