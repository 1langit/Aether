package com.pkmaether.aether.ui.auth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.pkmaether.aether.R
import com.pkmaether.aether.databinding.ActivityRegisterBinding
import com.pkmaether.aether.ui.common.TabAdapter

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top + 60, systemBars.right, systemBars.bottom)
            insets
        }

        title = "Register"
        setRegisterProgress(50)

        with(binding) {
            viewPager.adapter = TabAdapter(
                this@RegisterActivity,
                arrayOf(RegisterPage1Fragment(), RegisterPage2Fragment())
            )
            viewPager2 = viewPager
        }
    }

    fun navigateToPage(page: Int) {
        viewPager2.setCurrentItem(page, true)
    }

    fun setRegisterProgress(progress: Int) {
        binding.progressRegister.setProgress(progress, true)
    }
}