package com.pkmaether.aether.ui.statistic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.pkmaether.aether.R
import com.pkmaether.aether.databinding.ActivityStatisticsBinding
import com.pkmaether.aether.ui.common.TabAdapter

class StatisticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title = "Statistik"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            viewPager.adapter = TabAdapter(
                this@StatisticsActivity,
                arrayOf(
                    StatisticsFragment.newInstance("CO"),
                    StatisticsFragment.newInstance("SO2"),
                    StatisticsFragment.newInstance("NO2")
                )
            )
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when(position) {
                    0 -> "CO"
                    1 -> "SO2"
                    else -> "NO2"
                }
            }.attach()
        }
    }
}