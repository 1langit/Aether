package com.pkmaether.aether.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pkmaether.aether.R
import com.pkmaether.aether.data.PrefManager
import com.pkmaether.aether.data.repositories.FirestoreUserRepository
import com.pkmaether.aether.databinding.ActivityProfileBinding
import com.pkmaether.aether.ui.auth.LoginActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top + 60, systemBars.right, systemBars.bottom)
            insets
        }

        title = "Profil"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        prefManager = PrefManager.getInstance(this@ProfileActivity)

        with(binding) {
            txtCompany.text = prefManager.getCompanyName()
            txtType.text = prefManager.getIndustryType()
            btnEmail.text = prefManager.getEmail()
            btnPhone.text = prefManager.getPhone()
            btnAddress.text = prefManager.getAddress()

            btnLogout.setOnClickListener {
                val firestoreUserRepository = FirestoreUserRepository(this@ProfileActivity)
                firestoreUserRepository.logoutUser()
                val newIntent = Intent(this@ProfileActivity, LoginActivity::class.java)
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(newIntent)
                finish()
            }
        }
    }
}