package com.compilinghappen.fitnessandfinance.finance

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.compilinghappen.fitnessandfinance.databinding.FinanceCardItem1Binding
import com.compilinghappen.fitnessandfinance.databinding.FinanceCardItem2Binding
import com.compilinghappen.fitnessandfinance.room.CategoryDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


const val FINANCE_ADAPTER_ITEM_COUNTER_CARD = 0
const val FINANCE_ADAPTER_ITEM_LIMITED_CARD = 1

class FinanceAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(DataItemDiffUtilCallback()) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FINANCE_ADAPTER_ITEM_COUNTER_CARD -> CounterCardVH.from(parent)
            FINANCE_ADAPTER_ITEM_LIMITED_CARD -> LimitedCardVH.from(parent)
            else -> throw Exception("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val categoryItem = getItem(position).category
        when (holder) {
            is CounterCardVH -> holder.bind(categoryItem)
            is LimitedCardVH -> holder.bind(categoryItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.CounterCard -> FINANCE_ADAPTER_ITEM_COUNTER_CARD
            is DataItem.LimitedCard -> FINANCE_ADAPTER_ITEM_LIMITED_CARD
            else -> throw Exception("Unknown view type")
        }
    }

    class CounterCardVH private constructor(val binding: FinanceCardItem1Binding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): CounterCardVH {
                val context = parent.context
                val inflater = LayoutInflater.from(context)

                return CounterCardVH(FinanceCardItem1Binding.inflate(inflater, parent, false))
            }
        }

        fun bind(item: CategoryItem) {
            binding.categoryItem = item
            binding.executePendingBindings()
        }
    }

    class LimitedCardVH private constructor(val binding: FinanceCardItem2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): LimitedCardVH {
                val context = parent.context
                val inflater = LayoutInflater.from(context)

                return LimitedCardVH(FinanceCardItem2Binding.inflate(inflater, parent, false))
            }
        }

        fun bind(item: CategoryItem) {
            binding.categoryItem = item
            binding.executePendingBindings()
        }
    }

    fun applyData(categoryItems: List<CategoryItem>) {
        coroutineScope.launch {
            val items = ArrayList<DataItem>()
            withContext(Dispatchers.IO) {
                for (categoryItem in categoryItems) {
                    if (categoryItem.isCounter()) {
                        items.add(DataItem.CounterCard(categoryItem))
                    } else {
                        items.add(DataItem.LimitedCard(categoryItem))
                    }
                }
            }
            submitList(items)
        }
    }
}

class DataItemDiffUtilCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem(val category: CategoryItem) {
    class CounterCard(item: CategoryItem) : DataItem(item) {
        override val id = category.id
    }

    class LimitedCard(item: CategoryItem) : DataItem(item) {
        override val id = category.id
    }

    abstract val id: Long
}