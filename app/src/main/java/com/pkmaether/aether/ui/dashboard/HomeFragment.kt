package com.pkmaether.aether.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pkmaether.aether.R
import com.pkmaether.aether.databinding.FragmentHomeBinding
import com.pkmaether.aether.ui.chatbot.ChatbotActivity
import com.pkmaether.aether.ui.information.ReadActivity
import com.pkmaether.aether.ui.statistic.StatisticsActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            cardCurrentIspu.setOnClickListener {
                val newIntent = Intent(requireContext(), StatisticsActivity::class.java)
                startActivity(newIntent)
            }

            cardChatbot.setOnClickListener {
                val newIntent = Intent(requireContext(), ChatbotActivity::class.java)
                startActivity(newIntent)
            }

            featured.body.setOnClickListener {
                val newIntent = Intent(requireContext(), ReadActivity::class.java)
                startActivity(newIntent)
            }
        }
    }
}