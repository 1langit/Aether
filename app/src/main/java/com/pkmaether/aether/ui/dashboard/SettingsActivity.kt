package com.pkmaether.aether.ui.dashboard

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pkmaether.aether.R
import com.pkmaether.aether.data.PrefManager
import com.pkmaether.aether.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top + 60, systemBars.right, systemBars.bottom)
            insets
        }

        title = "Pengaturan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        prefManager = PrefManager.getInstance(this)

        with(binding) {
            btnNotif.setOnClickListener {
                showNotificationDialog()
            }

            btnAbout.setOnClickListener {
                showAboutSheet()
            }
        }
    }

    private fun showNotificationDialog() {
        val items = arrayOf("1/2 jam", "1 jam", "2 jam", "3 jam", "4 jam", "5 jam", "6 jam", "Nonaktifkan notifikasi")

        MaterialAlertDialogBuilder(this)
            .setTitle("Timer notifikasi")
            .setSingleChoiceItems(items, prefManager.getNotificationTimer()) { dialog, which ->
                prefManager.setNotificationTimer(which)
            }
            .setPositiveButton("Simpan") { dialog, which ->
                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, which ->
                dialog.dismiss()
            }.show()
    }

    private fun showAboutSheet() {

    }
}