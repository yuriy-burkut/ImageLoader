package com.yuriy.imageloader.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.yuriy.imageloader.entities.ImagesResult
import com.yuriy.imageloader.network.api.TenorApiService


class NetDataSourceFactory(
    private val searchRequest: String,
    private val apiService: TenorApiService
) : DataSource.Factory<Int, ImagesResult>() {

    val imagesDataSourceLiveData = MutableLiveData<NetDataSource>()

    override fun create(): DataSource<Int, ImagesResult> {
        val netDataSource = NetDataSource(searchRequest, apiService)
        imagesDataSourceLiveData.postValue(netDataSource)
        return netDataSource
    }
}