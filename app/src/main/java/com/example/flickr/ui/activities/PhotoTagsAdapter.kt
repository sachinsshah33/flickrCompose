package com.example.flickr.ui.activities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flickr.databinding.ListItemTagBinding

class PhotoTagsAdapter :
    ListAdapter<String, PhotoTagsAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {
    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<String> =
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(
                    oldItem: String,
                    newItem: String
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: String,
                    newItem: String
                ) = true
            }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        ListItemTagBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(private val itemBinding: ListItemTagBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: String) {
            itemBinding.apply {
                with(data) {
                    model = this
                }
            }
        }
    }
}