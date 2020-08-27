package com.yuriy.imageloader.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yuriy.imageloader.R
import com.yuriy.imageloader.entities.SavedImageInfo
import kotlinx.android.synthetic.main.saved_image_item.view.*

class FavoriteImagesAdapter(val callback: OnItemClickCallback) :
    PagedListAdapter<SavedImageInfo, FavoriteImagesAdapter.ViewHolder>(DiffUtilCallBack) {

    interface OnItemClickCallback {
        fun onImageClick(imageUrl: String)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteImagesAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.saved_image_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteImagesAdapter.ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: SavedImageInfo) = with(itemView) {

            iv_image_preview.setOnClickListener {
                callback.onImageClick(item.image_url)
            }

            tv_image_title.text = item.id
            Glide.with(this)
                .load(item.preview_url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(iv_image_preview)
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<SavedImageInfo>() {

        override fun areItemsTheSame(oldItem: SavedImageInfo, newItem: SavedImageInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SavedImageInfo, newItem: SavedImageInfo): Boolean {
            return oldItem.image_url == newItem.image_url
        }
    }
}