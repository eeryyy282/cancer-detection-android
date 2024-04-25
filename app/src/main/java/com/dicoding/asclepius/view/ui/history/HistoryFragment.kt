package com.dicoding.asclepius.view.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.AnalyzeResultEntity
import com.dicoding.asclepius.databinding.FragmentHistoryBinding
import com.dicoding.asclepius.view.ui.adapter.AnalyzeResultAdapter

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: HistoryViewModelFactory =
            HistoryViewModelFactory.getInstance(requireActivity())
        val historyViewModel: HistoryViewModel by viewModels {
            factory
        }

        val analyzeResultAdapter = AnalyzeResultAdapter()

        historyViewModel.getAnalyzeResult().observe(viewLifecycleOwner) { result ->
            binding.progressBar2.visibility = View.GONE
            val items = arrayListOf<AnalyzeResultEntity>()
            result.map {
                val item =
                    AnalyzeResultEntity(
                        id = it.id,
                        imageUri = it.imageUri,
                        analyzeResult = it.analyzeResult,
                        analyzeTime = it.analyzeTime
                    )
                items.add(item)
            }
            analyzeResultAdapter.submitList(items)

            if (items.isEmpty()) {
                binding.titleHistory.visibility = View.VISIBLE
                binding.titleHistory.text = R.string.tv_title_history.toString()
            } else {
                binding.titleHistory.visibility = View.GONE
            }
        }

        binding.rvAnalyzeResult.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = analyzeResultAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireActivity(),
                    GridLayoutManager(requireActivity(), 2).orientation
                )
            )
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}