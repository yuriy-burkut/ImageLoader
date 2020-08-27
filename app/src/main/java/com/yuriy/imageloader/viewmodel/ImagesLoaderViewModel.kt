package com.yuriy.imageloader.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.yuriy.imageloader.entities.ImageResult
import com.yuriy.imageloader.entities.SavedImageInfo
import com.yuriy.imageloader.repository.ImagesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ImagesLoaderViewModel(private val repository: ImagesRepository) : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val networkImageData: LiveData<PagedList<ImageResult>> = repository.networkImagesList
    val savedImagesDataInfo: LiveData<PagedList<SavedImageInfo>> = repository.savedImagesListInfo
    val searchRequestString: MutableLiveData<String> = MutableLiveData()

    val checkedImages: MutableLiveData<MutableMap<String, ImageResult>> = MutableLiveData()

    fun findImages(searchRequest: String) {
        repository.initSearch(searchRequest)
        searchRequestString.postValue(searchRequest)
    }

    fun invalidateList() {
        repository.netDataSourceFactory.sourceLiveData.value?.invalidate()
    }

    fun saveImages() = ioScope.launch {

        checkedImages.value?.forEach() { entry ->
            with(repository) {
                val imageInfo = saveImageToFileSystem(entry.value)
                imageInfo?.let {
                    saveImageDataToDatabase(it)
                }
            }
        }
    }

    fun openFullScreen(imageInfo: SavedImageInfo) {
        TODO("Not yet implemented")
    }
}