package com.hendyapp.mybooklist.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hendyapp.mblcore.domain.model.BookVolume
import com.hendyapp.mybooklist.R
import com.hendyapp.mybooklist.databinding.ItemBookBinding

class BookListAdapter(private val onClickAction: ((data: BookVolume) -> Unit)? = null,
                      private val isFavorite: Boolean = false):
    ListAdapter<BookVolume, BookListAdapter.BookViewHolder>(
    DiffCallback()
) {
    private class DiffCallback: ItemCallback<BookVolume>() {
        override fun areItemsTheSame(oldItem: BookVolume, newItem: BookVolume): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: BookVolume, newItem: BookVolume): Boolean =
            oldItem.id == newItem.id &&
            oldItem.title == newItem.title &&
            oldItem.smallImage == newItem.smallImage &&
            oldItem.desc == newItem.desc &&
            oldItem.favorite == newItem.favorite
    }

    inner class BookViewHolder(private val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BookVolume, onClickAction: ((data: BookVolume) -> Unit)?, isFavorite: Boolean) {
            if(data.image.isNotEmpty()) {
                Glide.with(binding.image)
                    .load(if(isFavorite) data.image else data.smallImage)
                    .placeholder(R.drawable.baseline_book_24)
                    .fitCenter()
                    .into(binding.image)
            }
            binding.title.text = data.title
            binding.favorite.visibility = if(data.favorite) View.VISIBLE else View.GONE
            itemView.setOnClickListener {
                onClickAction?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder =
        BookViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val data = currentList[position]
        holder.bind(data, onClickAction, isFavorite)
    }
}