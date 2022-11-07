package com.example.flickr.ui.activities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickr.databinding.ListItemPhotoBinding
import com.example.flickr.ui.models.PhotoLocal

class PhotosAdapter(
    val onItemSelected: ((PhotoLocal) -> Unit)? = null,
    val onMoreSelected: ((String) -> Unit)? = null
) :
    PagingDataAdapter<PhotoLocal, PhotosAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {
    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<PhotoLocal> =
            object : DiffUtil.ItemCallback<PhotoLocal>() {
                override fun areItemsTheSame(
                    oldItem: PhotoLocal,
                    newItem: PhotoLocal
                ) = oldItem.photoId == newItem.photoId

                override fun areContentsTheSame(
                    oldItem: PhotoLocal,
                    newItem: PhotoLocal
                ) = oldItem.photoUrl == newItem.photoUrl &&
                        oldItem.photoTitle == newItem.photoTitle &&
                        oldItem.ownerId == newItem.ownerId &&
                        oldItem.ownerProfileUrl == newItem.ownerProfileUrl &&
                        oldItem.ownerUsername == newItem.ownerUsername
            }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        ListItemPhotoBinding.inflate(
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

    inner class ViewHolder(private val itemBinding: ListItemPhotoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.chipRecyclerView.adapter = PhotoTagsAdapter()
        }

        fun bind(data: PhotoLocal) {
            itemBinding.apply {
                with(data) {
                    model = this
                    clickListener = onItemSelected
                    moreClickListener = onMoreSelected
                    (chipRecyclerView.adapter as? PhotoTagsAdapter)?.submitList(tags)
                    Glide.with(root.context)
                        .load(photoUrl)
                        .into(imageView)
                    Glide.with(root.context)
                        .load(ownerProfileUrl)
                        .into(imageViewProfilePic)
                }
            }
        }
    }
}