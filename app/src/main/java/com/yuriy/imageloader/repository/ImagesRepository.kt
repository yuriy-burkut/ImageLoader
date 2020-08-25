package com.yuriy.imageloader.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yuriy.imageloader.entities.ImageResult
import com.yuriy.imageloader.network.NetHelper
import com.yuriy.imageloader.network.api.TenorApiService
import com.yuriy.imageloader.utils.MessageUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

const val PAGE_SIZE: Int = 10
const val INITIAL_REQUEST_STRING = "universe"

class ImagesRepository(
    private val context: Context,
    private val apiService: TenorApiService,
    private val netHelper: NetHelper,
    private val messageUtils: MessageUtils
) {

    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val requestStringLiveData = MutableLiveData<String>("universe")
    private lateinit var networkImagesList: LiveData<PagedList<ImageResult>>
    lateinit var netDataSourceFactory: NetDataSourceFactory

    init {
        initNetDataSourceFactory()
    }

    private fun initNetDataSourceFactory() {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .setEnablePlaceholders(false)
            .build()

        netDataSourceFactory =
            NetDataSourceFactory(INITIAL_REQUEST_STRING, apiService, messageUtils)
        networkImagesList = LivePagedListBuilder(netDataSourceFactory, config).build()
    }

    fun initSearch(searchRequest: String) {
        netDataSourceFactory.newSearchRequest(searchRequest)
        requestStringLiveData.postValue(searchRequest)
    }

    fun getNetworkImages(): LiveData<PagedList<ImageResult>> {
        return if (this::networkImagesList.isInitialized) {
            networkImagesList
        } else MutableLiveData()
    }
}