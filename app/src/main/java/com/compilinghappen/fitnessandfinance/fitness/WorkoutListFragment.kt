package com.compilinghappen.fitnessandfinance.fitness

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.compilinghappen.fitnessandfinance.R
import com.compilinghappen.fitnessandfinance.databinding.FragmentFitnessBinding
import com.compilinghappen.fitnessandfinance.databinding.FragmentWorkoutListBinding
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.CornerSize
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class WorkoutListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentWorkoutListBinding.inflate(inflater, container, false);

        val leftShapePathModel = ShapeAppearanceModel().toBuilder()
        // You can change style and size
        leftShapePathModel.setTopLeftCorner(
            CornerFamily.ROUNDED,
            CornerSize { return@CornerSize 80F })

        leftShapePathModel.setTopRightCorner(
            CornerFamily.ROUNDED,
            CornerSize { return@CornerSize 80F })

        val bg = MaterialShapeDrawable(leftShapePathModel.build())
        // In my screen without applying color it shows black background
        bg.fillColor = ColorStateList.valueOf(
            requireContext().resources.getColor(android.R.color.white)
        )
        bg.elevation = 8F

        binding.cardView.background = bg
        binding.cardView.invalidate()

        return inflater.inflate(R.layout.fragment_workout_list, container, false)
    }
}