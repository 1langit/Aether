package com.pkmaether.aether.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pkmaether.aether.databinding.FragmentInformationBinding
import com.pkmaether.aether.ui.information.InformationAdapter
import com.pkmaether.aether.ui.information.InformationViewModel
import com.pkmaether.aether.ui.information.ReadActivity

class InformationFragment : Fragment() {

    private lateinit var binding: FragmentInformationBinding
    private val informationViewModel: InformationViewModel by viewModels()
    private val informationAdapter = InformationAdapter { article ->
        val newIntent = Intent(requireContext(), ReadActivity::class.java)
        newIntent.putExtra("ARTICLE", article)
        startActivity(newIntent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvInformation.apply {
                adapter = informationAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            informationViewModel.articles.observe(viewLifecycleOwner) { articles ->
                informationAdapter.setArticles(articles)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        informationViewModel.setDummyArticles()
    }
}