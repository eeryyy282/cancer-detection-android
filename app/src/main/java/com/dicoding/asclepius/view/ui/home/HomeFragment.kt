package com.dicoding.asclepius.view.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.Result
import com.dicoding.asclepius.databinding.FragmentHomeBinding
import com.dicoding.asclepius.view.MainActivity
import com.dicoding.asclepius.view.ui.adapter.ArticleAdapter
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.cancerScannerHome.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: HomeViewModelFactory = HomeViewModelFactory.getInstance()
        val homeViewModel: HomeViewModel by viewModels {
            factory
        }

        val articleAdapter = ArticleAdapter()

        if (homeViewModel.articles.value == null) {
            homeViewModel.findArticles()
        }

        homeViewModel.articles.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val articleData = result.data.filterNotNull()
                        articleAdapter.submitList(articleData)
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        homeViewModel.findArticles()
                        Snackbar.make(
                            view,
                            "Gagal memuat artikel",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.rvArticles.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = articleAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager(requireActivity()).orientation
                )
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}