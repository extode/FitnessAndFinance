package com.compilinghappen.fitnessandfinance

import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.math.MathUtils
import androidx.databinding.BindingAdapter
import com.compilinghappen.fitnessandfinance.finance.CategoryItem
import java.math.BigDecimal

@BindingAdapter("cash")
fun TextView.setCash(bd: BigDecimal?) {
    bd?.let {
        text = String.format("%s ₽", bd.toString())
    }
}

@BindingAdapter("cashProgress")
fun ProgressBar.setCashProgress(ci: CategoryItem?) {
    ci?.let {
        val c1 = ci.cash.toDouble()
        val c2 = ci.limit!!.toDouble()
        progress = MathUtils.clamp(c1 / c2 * 100.0, 0.0, 100.0).toInt()
    }
}