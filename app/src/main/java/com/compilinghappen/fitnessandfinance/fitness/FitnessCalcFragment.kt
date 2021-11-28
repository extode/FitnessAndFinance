package com.compilinghappen.fitnessandfinance.fitness

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.compilinghappen.fitnessandfinance.R
import com.compilinghappen.fitnessandfinance.databinding.FitnessDataItemBinding
import com.compilinghappen.fitnessandfinance.databinding.FragmentFitnessCalcBinding
import com.compilinghappen.fitnessandfinance.room.FNFDatabase
import com.compilinghappen.fitnessandfinance.room.FitnessData
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class FitnessCalcFragment : Fragment() {

    private val viewModel: FitnessCalcViewModel by viewModels {
        FitnessCalcViewModelFactory(FNFDatabase.getInstance(requireContext()).fitnessDataDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentFitnessCalcBinding.inflate(inflater, container, false)

        val sexItems = listOf<String>("Мужской", "Женский")
        val sexAdapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, sexItems)
        binding.sexField.adapter = sexAdapter

        val activeItems = listOf<String>(
            "Сидячий образ жизни",
            "Лёгкая (1-3 раза в неделю)",
            "Средняя (3-5 раз в неделю)",
            "Высокая (6-7 раз в неделю)",
            "Экстремальная (больше 1 раза в день)"
        )
        val activeAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            activeItems
        )
        binding.activeField.adapter = activeAdapter

        val targetItems = listOf<String>("Сбросить вес", "Поддерживать форму", "Набрать вес")
        val targetAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            targetItems
        )
        binding.targetField.adapter = targetAdapter


        binding.floatingButton.setOnClickListener() {
            val mass: Double = binding.massValue.text.toString().toDouble()
            val height: Double = binding.heightValue.text.toString().toDouble()
            val age: Int = binding.ageValue.text.toString().toInt()
            val sex: String = binding.sexField.selectedItem.toString()
            val target: String = binding.targetField.selectedItem.toString()
            val active: String = binding.activeField.selectedItem.toString()
            var imb = 0.0
            var ccal = 0
            var protein = 0
            var fats = 0
            var carbs = 0
            var activeChange = 0.0
            var massChange = 0.0
            val activeId: Int = binding.activeField.selectedItemId.toInt()
            val sexId: Int = binding.sexField.selectedItemId.toInt()
            val targetInt = binding.targetField.selectedItemId.toInt()

            when (activeId) {
                0 -> activeChange = 1.2
                1 -> activeChange = 1.375
                2 -> activeChange = 1.55
                3 -> activeChange = 1.725
                4 -> activeChange = 1.9
            }

            when (targetInt) {
                0 -> massChange = mass / (0.45 * 4)
                1 -> massChange = mass
                2 -> {
                    massChange = mass
                    ccal = 467
                }
            }

            when (sexId) {
                0 -> {
                    imb = mass / (height * height) * 10000
                    ccal += ((88.36 + (13.4 * massChange) + (4.8 * height) - (5.7 * age)) * activeChange).toInt()
                    protein = ((ccal * 0.3) / 4).toInt()
                    fats = ((ccal * 0.3) / 9).toInt()
                    carbs = ((ccal * 0.4) / 4).toInt()
                }
                1 -> {
                    imb = mass / (height * height) * 10000
                    ccal += ((447.6 + (9.2 * massChange) + (3.1 * height) - (4.3 * age)) * activeChange).toInt()
                    protein = ((ccal * 0.3) / 4).toInt()
                    fats = ((ccal * 0.3) / 9).toInt()
                    carbs = ((ccal * 0.4) / 4).toInt()
                }
            }
            binding.indexMassBodyValue.text = ("%.2f".format(imb))
            binding.ccalValue.text = ccal.toString()
            binding.proteinValue.text = protein.toString()
            binding.fatsValue.text = fats.toString()
            binding.carbsValue.text = carbs.toString()
        }

        binding.fitnessCalcFabAdd.setOnClickListener {
            val view = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_fitness_data_dialog, null, false)
            AlertDialog.Builder(requireContext())
                .setTitle("Добавление")
                .setView(view)
                .setPositiveButton("Ок") { _, _ ->
                    val weight = view.findViewById<EditText>(R.id.fdd_weight_text).text.toString()
                    val waistCircumference =
                        view.findViewById<EditText>(R.id.fdd_waistCircumference_text).text.toString()
                    viewModel.addNew(FitnessData(0L, weight, waistCircumference))
                }
                .setNeutralButton("Отмена") { a1, a2 ->
                    a1.dismiss()
                }
                .setCancelable(true)
                .create().show()
        }

        val adapter = FitnessAdapter(emptyList())
        binding.fitnessCalcRecyclerview.adapter = adapter

        viewModel.fitnessList.observe(viewLifecycleOwner) {
            adapter.applyData(it)
        }

        return binding.root
    }
}


class FitnessAdapter(private var data: List<FitnessData>) :
    RecyclerView.Adapter<FitnessAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FitnessDataItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.fdiWeightText.text = item.weight
        holder.binding.fdiWaistCircumferenceText.text = item.waistCircumference

        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
        val formatted = dateFormat.format(item.date)
        holder.binding.fdiDate.text = formatted
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun applyData(data: List<FitnessData>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: FitnessDataItemBinding) : RecyclerView.ViewHolder(binding.root) {}
}