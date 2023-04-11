package com.example.pairmatch.ui.news

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pairmatch.R
import com.example.pairmatch.databinding.ItemNewsBinding
import com.example.pairmatch.entites.News

class NewsAdapter(private val onClick: (data: News) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.VH>() {

    var items: MutableList<News> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_news, parent, false), onClick
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    class VH(itemView: View, private val onClick: (News) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemNewsBinding.bind(itemView)

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(data: News) {
            binding.run {

                Glide.with(itemView.context).load(data.logo).into(ivPicture)

                tvDate.text = data.date
                tvTitle.text = data.header
                tvSmallDescription.text = data.text
            }
            itemView.setOnClickListener {
                onClick(data)
            }
        }
    }
}