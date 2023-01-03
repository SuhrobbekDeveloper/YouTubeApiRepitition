package com.example.youtubeapirepitition.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapirepitition.databinding.ItemYoutubeBinding
import com.example.youtubeapirepitition.models.Item
import com.squareup.picasso.Picasso

class YouTubeAdapter(var listener: OnItemClickListener) :
    ListAdapter<Item, YouTubeAdapter.Vh>(MyDiffUtil()) {


    class MyDiffUtil : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }

    inner class Vh(var itemUserBinding: ItemYoutubeBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {

        fun onBind(item: Item) {
            itemUserBinding.apply {
                Picasso.get().load(item.snippet.thumbnails.default.url).into(image)
                videoTitle.text = item.snippet.title
            }
            itemView.setOnClickListener {
                listener.onItemClick(item.id.videoId)
            }
        }
    }


    interface OnItemClickListener {
        fun onItemClick(videoId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemYoutubeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }
}