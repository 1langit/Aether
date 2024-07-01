package com.pkmaether.aether.ui.information

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pkmaether.aether.data.models.Article
import com.pkmaether.aether.data.models.ChatMessage
import com.pkmaether.aether.databinding.ItemInformationBinding

class InformationAdapter(private val onItemClick: (Article) -> Unit) : RecyclerView.Adapter<InformationAdapter.ArticleViewHolder>() {

    private val articleList = ArrayList<Article>()

    inner class ArticleViewHolder(private val binding: ItemInformationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                txtTitle.text = article.title
                txtPreview.text = article.source
                Glide.with(root).load(article.imageUrl).into(imgInformation)
            }

            itemView.setOnClickListener {
                onItemClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemInformationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    fun setArticles(articles: List<Article>) {
        articleList.clear()
        articleList.addAll(articles)
        notifyDataSetChanged()
    }
}