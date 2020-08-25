package com.yuriy.imageloader.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.yuriy.imageloader.entities.ImageResult
import com.yuriy.imageloader.repository.ImagesRepository

class ImagesLoaderViewModel(private val repository: ImagesRepository) : ViewModel() {

    val networkImageData: LiveData<PagedList<ImageResult>> = repository.getNetworkImages()

    fun findImages(searchRequest: String) = repository.initSearch(searchRequest)

    fun invalidateList() {
        repository.netDataSourceFactory.sourceLiveData.value?.invalidate()
    }

}