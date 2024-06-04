package com.pkmaether.aether.ui.statistic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.pkmaether.aether.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding

    companion object {
        private const val ARG_NAME = "name"

        fun newInstance(name: String): StatisticsFragment {
            val fragment = StatisticsFragment()
            val args = Bundle()
            args.putString(ARG_NAME, name)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chartExample()

        with(binding) {
            txtParticulate.text = "Kadar ${arguments?.getString(ARG_NAME)}"
        }
    }

    private fun chartExample() {
        val entries = ArrayList<Entry>()
        entries.add(Entry(1f, 1f))
        entries.add(Entry(2f, 2f))
        entries.add(Entry(3f, 0f))
        entries.add(Entry(4f, 4f))
        entries.add(Entry(5f, 3f))

        val dataSet = LineDataSet(entries, "Label")
        val lineData = LineData(dataSet)

        binding.chartLine.data = lineData

        val description = Description()
        description.text = "Line Chart Example"
        binding.chartLine.description = description

        binding.chartLine.invalidate()
    }
}