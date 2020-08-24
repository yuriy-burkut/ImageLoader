package com.yuriy.imageloader.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuriy.imageloader.R
import com.yuriy.imageloader.ui.activities.MainActivity
import com.yuriy.imageloader.ui.adapters.LoadedImagesAdapter
import com.yuriy.imageloader.viewmodel.ImagesLoaderViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_fragment.*

class FindImagesFragment : Fragment() {

    private val viewModel: ImagesLoaderViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            (requireActivity() as MainActivity).vmFactory
        ).get(ImagesLoaderViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, view_pager, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_found_items.apply {
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(this@FindImagesFragment.context)
            adapter = LoadedImagesAdapter()

            et_search_images.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    TODO()
                }

                return@setOnEditorActionListener true
            }
        }
    }

}