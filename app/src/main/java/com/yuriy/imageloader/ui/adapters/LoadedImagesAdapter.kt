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
import com.yuriy.imageloader.entities.ImageResult
import kotlinx.android.synthetic.main.found_image_item.view.*

class LoadedImagesAdapter(val callback: OnItemClickCallback) :
    PagedListAdapter<ImageResult, LoadedImagesAdapter.ViewHolder>(DiffUtilCallBack) {

    interface OnItemClickCallback {
        fun onImageClick(item: ImageResult)
        fun onCheckBoxStateChange()
    }

    val checkedImages: MutableMap<String, ImageResult> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.found_image_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
            holder.setChecked(checkedImages.containsKey(it.id))
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ImageResult?) = with(itemView) {

            item?.let {
                setListeners(it)
                tv_image_title.text = it.id
                Glide.with(this)
                    .load(it.media[0].gif.previewUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv_image_preview)
            }
        }

        fun setChecked(state: Boolean) {
            itemView.chb_select_to_favorites.isChecked = state
        }

        private fun setListeners(item: ImageResult) = with(itemView) {

            iv_image_preview.setOnClickListener {
                callback.onImageClick(item)
            }

            chb_select_to_favorites.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkedImages[item.id] = item
                } else {
                    checkedImages.remove(item.id)
                }
                callback.onCheckBoxStateChange()
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