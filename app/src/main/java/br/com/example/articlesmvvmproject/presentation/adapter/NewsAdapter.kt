package br.com.example.articlesmvvmproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.example.articlesmvvmproject.R
import br.com.example.articlesmvvmproject.data.model.Article
import br.com.example.articlesmvvmproject.databinding.ItemNewsListAdapterBinding
import br.com.example.articlesmvvmproject.utils.NO_IMAGE_URL
import com.bumptech.glide.Glide

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    //verifica diferenças entre listas de modo assincrono
     val differ = AsyncListDiffer(this, callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewsListAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int = differ.currentList.size


    class MyViewHolder(private val binding: ItemNewsListAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                tvTitle.text = article.title
                tvDescription.text = article.description
                tvPublishedAt.text = article.publishedAt
                tvSource.text = article.source.name
                tvAuthor.text = article.author

                if(article.urlToImage?.isNotEmpty() == true || article.urlToImage != null){
                    Glide.with(binding.ivArticleImage.context).load(article.urlToImage)
                        .into(ivArticleImage)
                }


                else Glide.with(binding.ivArticleImage.context).load(NO_IMAGE_URL)
                    .into(ivArticleImage)
            }
        }
    }
}