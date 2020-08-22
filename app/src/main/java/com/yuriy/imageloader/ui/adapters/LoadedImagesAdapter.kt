package com.yuriy.imageloader.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuriy.imageloader.R
import kotlinx.android.synthetic.main.found_image_item.view.*

class LoadedImagesAdapter : RecyclerView.Adapter<LoadedImagesAdapter.ViewHolder>() {

    private val list: List<String> = List<String>(55) {
        "Item $it"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.found_image_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) = with(itemView) {
            tv_image_title.text = item
        }
    }
}