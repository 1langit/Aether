package com.pkmaether.aether.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pkmaether.aether.R
import com.pkmaether.aether.data.PrefManager
import com.pkmaether.aether.data.repositories.UserRepository
import com.pkmaether.aether.databinding.ActivityLoginBinding
import com.pkmaether.aether.ui.dashboard.DashboardActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top + 60, systemBars.right, systemBars.bottom)
            insets
        }

        if (isLoggedin()) {
            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
            finish()
        }

        title = "Login"

        with(binding) {
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                if (email.isNotBlank() && password.isNotBlank()) {
                    val userRepository = UserRepository(this@LoginActivity)
                    userRepository.loginUser(
                        email,
                        password,
                        {
                            Toast.makeText(this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                            finish()
                        },
                        { exception ->
                            Toast.makeText(this@LoginActivity, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

            linkRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }
        }
    }

    private fun isLoggedin(): Boolean {
        val prefManager = PrefManager.getInstance(this@LoginActivity)
        return prefManager.getUid().isNotBlank()
    }
}