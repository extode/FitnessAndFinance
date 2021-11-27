package com.compilinghappen.fitnessandfinance.categoryeditor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.compilinghappen.fitnessandfinance.R
import com.compilinghappen.fitnessandfinance.room.FNFDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CategoryEditorFragment : Fragment() {
    private val viewModel: CategoryEditorViewModel by viewModels {
        CategoryEditorViewModelFactory(FNFDatabase.getInstance(requireContext()).categoryDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category_editor, container, false)

        val recyclerview: RecyclerView = view.findViewById(R.id.categories_recyclerview)
        val adapter = CategoryEditorAdapter(emptyList())

        recyclerview.adapter = adapter

        viewModel.categoriesList.observe(viewLifecycleOwner) {
            adapter.applyData(it)
        }

        viewModel.createNewCategoryEvent.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    CategoryEditorFragmentDirections.actionCategoryEditorFragmentToCategoryBuilderFragment()
                )
                viewModel.createNewCategoryEventHandled()
            }
        }

        view.findViewById<FloatingActionButton>(R.id.fab_add).setOnClickListener {
            viewModel.addNewCategory()
        }

        val rawArgs = arguments
        if (rawArgs != null && rawArgs.containsKey("newCategoryName")) {
            val args = CategoryEditorFragmentArgs.fromBundle(rawArgs)
            viewModel.handleArgs(args.newCategoryName, args.newCategoryFilters, args.newCategoryLimit)
        }

        return view
    }
}