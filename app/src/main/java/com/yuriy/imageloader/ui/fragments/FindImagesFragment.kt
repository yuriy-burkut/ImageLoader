package com.yuriy.imageloader.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuriy.imageloader.R
import com.yuriy.imageloader.entities.ImageResult
import com.yuriy.imageloader.ui.activities.MainActivity
import com.yuriy.imageloader.ui.adapters.LoadedImagesAdapter
import com.yuriy.imageloader.viewmodel.ImagesLoaderViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_fragment.*

class FindImagesFragment : Fragment(), LoadedImagesAdapter.OnItemClickCallback {

    private val viewModel: ImagesLoaderViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            (requireActivity() as MainActivity).vmFactory
        ).get(ImagesLoaderViewModel::class.java)
    }

    private val listAdapter: LoadedImagesAdapter by lazy {
        LoadedImagesAdapter(this)
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

        initList()
        initEditText()
        observeLiveData()
        setListeners()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {

        viewModel.checkedImages.value?.let {
            listAdapter.checkedImages.putAll(viewModel.checkedImages.value!!)
        }

        viewModel.searchRequestString.value?.let {
            et_search_images.setText(it)
        }

        super.onViewStateRestored(savedInstanceState)
    }

    private fun initEditText() {
        et_search_images.setOnEditorActionListener { v, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                findImages(v.text.toString())
            }

            return@setOnEditorActionListener true
        }
    }

    private fun initList() {
        rv_found_images_list.apply {
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(this@FindImagesFragment.context)
            adapter = listAdapter
        }
    }

    private fun setListeners() {
        fab_save_button.setOnClickListener {
            viewModel.saveImages()
            clearChecked()
        }
    }

    private fun observeLiveData() {
        viewModel.networkImageData.observe(viewLifecycleOwner, Observer {
            listAdapter.submitList(it)
        })

        viewModel.checkedImages.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                fab_save_button.show()
            } else fab_save_button.hide()
        })
    }

    private fun clearChecked() {
        listAdapter.checkedImages.clear()
        viewModel.checkedImages.value = mutableMapOf()
        listAdapter.notifyDataSetChanged()
    }

    override fun onImageClick(item: ImageResult) {
        TODO()
    }

    override fun onCheckBoxStateChange() {
        viewModel.checkedImages.value = listAdapter.checkedImages
    }

    private fun findImages(searchRequest: String) {
        val request = searchRequest.trim()

        if (request.isNotBlank()) {
            viewModel.findImages(request)
            viewModel.invalidateList()
            clearChecked()
            listAdapter.submitList(null)
        }
    }

}
