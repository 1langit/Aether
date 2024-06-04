package com.pkmaether.aether.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.pkmaether.aether.databinding.FragmentRegisterPage1Binding

class RegisterPage1Fragment : Fragment() {

    private lateinit var binding: FragmentRegisterPage1Binding
    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterPage1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnNext.setOnClickListener {
                val parentActivity = requireActivity() as RegisterActivity
                parentActivity.navigateToPage(1)
                parentActivity.setRegisterProgress(100)

                viewModel.email = edtEmail.text.toString()
                viewModel.password = edtPassword.text.toString()
            }

            linkLogin.setOnClickListener {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
    }
}