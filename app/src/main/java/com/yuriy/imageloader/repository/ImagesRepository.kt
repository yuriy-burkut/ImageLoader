package com.yuriy.imageloader.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.yuriy.imageloader.entities.ImagesResult
import com.yuriy.imageloader.network.NetHelper
import com.yuriy.imageloader.network.api.TenorApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

const val PAGE_SIZE: Int = 10

class ImagesRepository(
    private val api: TenorApiService,
    private val netHelper: NetHelper
) {

    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    lateinit var imagesList: LiveData<PagedList<ImagesResult>>



}