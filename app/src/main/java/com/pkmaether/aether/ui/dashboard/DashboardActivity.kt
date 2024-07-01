package com.pkmaether.aether.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pkmaether.aether.R
import com.pkmaether.aether.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        supportActionBar?.hide()

        with(binding) {
            val navController = findNavController(R.id.nav_host_fragment)
            bottomNavigation.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                toolbar.title = when (destination.label) {
                    "fragment_history" -> "Riwayat"
                    "fragment_information" -> "Informasi"
                    else -> "AETHER"
                }
            }

            toolbar.setNavigationOnClickListener {
                val newIntent = Intent(this@DashboardActivity, SettingsActivity::class.java)
                startActivity(newIntent)
            }

            toolbar.setOnMenuItemClickListener {
                val newIntent = Intent(this@DashboardActivity, ProfileActivity::class.java)
                startActivity(newIntent)
                true
            }
        }
    }
}