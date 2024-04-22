package com.dicoding.asclepius.view.ui.adapter

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dicoding.asclepius.data.local.entity.ArticleEntity
import com.dicoding.asclepius.view.ui.adapter.ArticleAdapter.MyViewHolder

class ArticleAdapter() : ListAdapter<ArticleEntity, MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class MyViewHolder {

    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ArticleEntity> =
            object : DiffUtil.ItemCallback<ArticleEntity>() {
                override fun areItemsTheSame(
                    oldItem: ArticleEntity,
                    newItem: ArticleEntity
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: ArticleEntity,
                    newItem: ArticleEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

}