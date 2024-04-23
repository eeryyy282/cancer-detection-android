package com.dicoding.asclepius.view.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.ItemArticlesBinding

class ArticleAdapter : ListAdapter<ArticlesItem, ArticleAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemArticlesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleAdapter.MyViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    override fun submitList(list: List<ArticlesItem>?) {
        super.submitList(list?.filterNotNullItems())
    }

    private fun List<ArticlesItem>.filterNotNullItems(): List<ArticlesItem> {
        return filter { article ->
            article.author != null &&
                    article.urlToImage != null &&
                    article.title != null &&
                    article.publishedAt != null
        }
    }

    class MyViewHolder(private val binding: ItemArticlesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticlesItem) {
            binding.tvAuthor.text = "Oleh ${article.author.toString()}"
            Glide.with(binding.root)
                .load(article.urlToImage)
                .into(binding.ivArticle)
            binding.tvTitleArticles.text = article.title
            binding.tvPublishedAt.text = article.publishedAt
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ArticlesItem> =
            object : DiffUtil.ItemCallback<ArticlesItem>() {
                override fun areItemsTheSame(
                    oldItem: ArticlesItem,
                    newItem: ArticlesItem
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: ArticlesItem,
                    newItem: ArticlesItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }


}
