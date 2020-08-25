package com.yuriy.imageloader.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.yuriy.imageloader.entities.ImageResult
import com.yuriy.imageloader.network.api.TenorApiService
import com.yuriy.imageloader.utils.MessageUtils


class NetDataSourceFactory(
    private var searchRequest: String,
    private val apiService: TenorApiService,
    private val messageUtils: MessageUtils
) : DataSource.Factory<Int, ImageResult>() {

    val sourceLiveData =
        MutableLiveData<NetDataSource>()

    override fun create(): DataSource<Int, ImageResult> {
        val source = NetDataSource(searchRequest, apiService, messageUtils)
        sourceLiveData.postValue(source)
        return source
    }

    fun newSearchRequest(newSearchRequest: String) {
        searchRequest = newSearchRequest
        sourceLiveData.value?.invalidate()
    }
}