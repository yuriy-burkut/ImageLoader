package com.yuriy.imageloader.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.squareup.picasso.Picasso
import com.yuriy.imageloader.R
import com.yuriy.imageloader.entities.ImageResult
import kotlinx.android.synthetic.main.found_image_item.view.*

class LoadedImagesAdapter :
    PagedListAdapter<ImageResult, LoadedImagesAdapter.ViewHolder>(DiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.found_image_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { holder.bind(it) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ImageResult?) = with(itemView) {
            item?.let {
                tv_image_title.text = it.id
                Glide.with(this)
                    .load(it.media[0].gif.previewUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv_image_preview)
            }
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<ImageResult>() {

        override fun areItemsTheSame(oldItem: ImageResult, newItem: ImageResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageResult, newItem: ImageResult): Boolean {
            return oldItem.media[0].gif.imageUrl == newItem.media[0].gif.imageUrl
        }
    }
}