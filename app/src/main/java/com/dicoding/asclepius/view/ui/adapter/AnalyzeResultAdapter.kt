package com.dicoding.asclepius.view.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.local.entity.AnalyzeResultEntity
import com.dicoding.asclepius.databinding.ItemResultAnalyzeBinding

class AnalyzeResultAdapter(
    private val onDeleteClick: (AnalyzeResultEntity) -> Unit
) :
    ListAdapter<AnalyzeResultEntity, AnalyzeResultAdapter.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemResultAnalyzeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val resultAnalyze = getItem(position)
        holder.bind(resultAnalyze)
    }

    class MyViewHolder(
        private val binding: ItemResultAnalyzeBinding,
        private val onDeleteClick: (AnalyzeResultEntity) -> Unit
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        @SuppressLint("SetTextI18n")
        fun bind(result: AnalyzeResultEntity) {
            binding.tvAnalyzeResult.text = result.analyzeResult
            binding.tvAnalyzeTime.text = "Disimpan pada\n" + result.analyzeTime
            Glide.with(itemView.context)
                .load(result.imageUri)
                .into(binding.ivImageResultAnalyze)

            binding.btnDelete.setOnClickListener {
                onDeleteClick(result)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<AnalyzeResultEntity> =
            object : DiffUtil.ItemCallback<AnalyzeResultEntity>() {
                override fun areItemsTheSame(
                    oldItem: AnalyzeResultEntity,
                    newItem: AnalyzeResultEntity
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: AnalyzeResultEntity,
                    newItem: AnalyzeResultEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}


