package com.compilinghappen.fitnessandfinance.categoryeditor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.compilinghappen.fitnessandfinance.databinding.CategoryItemBinding
import com.compilinghappen.fitnessandfinance.room.CategoryDb

class CategoryEditorAdapter(private var categories: List<CategoryDb>) :
    RecyclerView.Adapter<CategoryEditorAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun applyData(categories: List<CategoryDb>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    class ViewHolder private constructor(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val context = parent.context
                val inflater = LayoutInflater.from(context)
                val binding = CategoryItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(category: CategoryDb) {
            title = category.name
            filters = category.parseFilters().joinToString { it }
        }

        var title: String
            get() = binding.categoryItemTitle.text.toString()
            set(value) {
                binding.categoryItemTitle.text = value
            }

        var filters: String
            get() = binding.categoryItemFiltersText.text.toString()
            set(value){
                binding.categoryItemFiltersText.text = value
            }
    }

}