package com.yuriy.imageloader.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.yuriy.imageloader.R
import com.yuriy.imageloader.ui.activities.IMAGE_URL_KEY
import kotlinx.android.synthetic.main.fullscreen_image_view_fragment.*

class FullscreenImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fullscreen_image_view_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUrl = arguments?.getString(IMAGE_URL_KEY)

        imageUrl?.let {
            Glide.with(requireView())
                .asGif()
                .load(it)
                .into(iv_fullscreen_image_view)
        }
    }

}