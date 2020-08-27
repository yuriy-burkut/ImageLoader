package com.yuriy.imageloader.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuriy.imageloader.R
import com.yuriy.imageloader.livadata.ViewAction
import com.yuriy.imageloader.ui.activities.MainActivity
import com.yuriy.imageloader.ui.adapters.FavoriteImagesAdapter
import com.yuriy.imageloader.viewmodel.ImagesLoaderViewModel
import kotlinx.android.synthetic.main.foavorites_fragment.*
import kotlinx.android.synthetic.main.tabs_host_fragment.*

class FavoritesImagesFragment : Fragment(), FavoriteImagesAdapter.OnItemClickCallback {

    private val viewModel: ImagesLoaderViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            (requireActivity() as MainActivity).vmFactory
        ).get(ImagesLoaderViewModel::class.java)
    }

    private val listAdapter: FavoriteImagesAdapter by lazy {
        FavoriteImagesAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.foavorites_fragment, view_pager, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        observeLiveData()
    }

    override fun onImageClick(imageUrl: String) {
        viewModel.viewAction.postValue(ViewAction.ShowFullScreen(imageUrl))
    }

    private fun initList() {
        rv_saved_images_list.apply {
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(this@FavoritesImagesFragment.context)
            adapter = listAdapter
        }
    }

    private fun observeLiveData() {
        viewModel.savedImagesDataInfo.observe(viewLifecycleOwner, Observer {
            listAdapter.submitList(it)
        })
    }
}