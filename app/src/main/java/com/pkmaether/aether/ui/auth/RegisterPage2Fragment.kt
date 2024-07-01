package com.pkmaether.aether.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pkmaether.aether.data.models.User
import com.pkmaether.aether.data.repositories.UserRepository
import com.pkmaether.aether.databinding.FragmentRegisterPage2Binding
import com.pkmaether.aether.ui.dashboard.DashboardActivity

class RegisterPage2Fragment : Fragment() {

    private lateinit var binding: FragmentRegisterPage2Binding
    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterPage2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnRegister.setOnClickListener {
                val userRepository = UserRepository(requireContext())
                userRepository.registerUser(
                    User(
                        email = viewModel.email,
                        companyName = edtPerusahaan.text.toString(),
                        industryType = edtIndustri.text.toString(),
                        address = edtAlamat.text.toString(),
                        phone = edtTelepon.text.toString()
                    ),
                    viewModel.password,
                    {
                        startActivity(Intent(requireContext(), DashboardActivity::class.java))
                        Toast.makeText(requireContext(), "Register berhasil", Toast.LENGTH_SHORT).show()
                        requireActivity().finish()
                    },
                    { exception ->
                        Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}