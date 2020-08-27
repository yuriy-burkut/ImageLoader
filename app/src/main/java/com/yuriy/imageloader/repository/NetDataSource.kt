package com.yuriy.imageloader.repository

import androidx.paging.PageKeyedDataSource
import com.yuriy.imageloader.entities.ImageResult
import com.yuriy.imageloader.network.api.TenorApiService
import com.yuriy.imageloader.utils.MessageUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException

class NetDataSource(
    private var searchRequest: String,
    private val api: TenorApiService,
    private val massageUtils: MessageUtils
) :
    PageKeyedDataSource<Int, ImageResult>() {

    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ImageResult>
    ) {

        if (searchRequest.isBlank()) {
            callback.onResult(listOf(), null, null)
            return
        }


        ioScope.launch {
            try {
                val result = api.getImages(searchRequest, params.requestedLoadSize)
                if (result != null) {
                    callback.onResult(result.imageResults ?: listOf(), null, result.next)
                }
            } catch (exception: IOException) {
                massageUtils.showError(exception)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ImageResult>) {

        if (searchRequest.isBlank()) {
            callback.onResult(listOf(), null)
            return
        }

        if (getPrevKey(params.key, params.requestedLoadSize) == null) {
            callback.onResult(listOf(), params.requestedLoadSize)
        } else {

            ioScope.launch {
                try {
                    val result = api.getImages(searchRequest, params.requestedLoadSize)
                    if (result != null) {
                        callback.onResult(
                            result.imageResults ?: listOf(),
                            getPrevKey(result.next, params.requestedLoadSize)
                        )
                    }
                } catch (exception: IOException) {
                    massageUtils.showError(exception)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImageResult>) {

        if (searchRequest.isBlank()) {
            callback.onResult(listOf(), null)
            return
        }

        ioScope.launch {
            try {
                val result = api.getImages(searchRequest, params.requestedLoadSize, params.key)
                if (result != null) {
                    callback.onResult(result.imageResults ?: listOf(), result.next)
                }
            } catch (exception: IOException) {
                massageUtils.showError(exception)
            }
        }
    }

    private fun getPrevKey(next: Int, loadSize: Int): Int? {
        val prevKey = next - loadSize * 2
        return if (prevKey > 0) {
            prevKey
        } else null
    }
}