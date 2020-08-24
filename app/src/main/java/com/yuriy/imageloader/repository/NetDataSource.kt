package com.yuriy.imageloader.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.yuriy.imageloader.entities.ImagesResult
import com.yuriy.imageloader.network.api.State
import com.yuriy.imageloader.network.api.TenorApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException

class NetDataSource(private val searchRequest: String, private val api: TenorApiService) :
    PageKeyedDataSource<Int, ImagesResult>() {

    val state = MutableLiveData<State>()

    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ImagesResult>
    ) {
        updateState(State.LOADING)

        ioScope.launch {
            try {
                val result = api.getImages(searchRequest, params.requestedLoadSize)
                if (result != null) {
                    updateState(State.DONE)
                    callback.onResult(result.imagesResults ?: listOf(), null, result.next)
                } else {
                    updateState(State.ERROR)
                }
            } catch (exception: IOException) {
                throw exception
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ImagesResult>) {

        val position = if ((params.key - params.requestedLoadSize * 2) > 0) {
            params.key - params.requestedLoadSize * 2
        } else 0

        if (position == 0) {
            callback.onResult(listOf(), params.requestedLoadSize)
        } else {
            updateState(State.LOADING)

            ioScope.launch {
                try {
                    val result = api.getImages(searchRequest, params.requestedLoadSize, position)
                    if (result != null) {
                        updateState(State.DONE)
                        callback.onResult(
                            result.imagesResults ?: listOf(),
                            params.requestedLoadSize
                        )
                    } else {
                        updateState(State.ERROR)
                    }
                } catch (exception: IOException) {
                    throw exception
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImagesResult>) {
        updateState(State.LOADING)

        ioScope.launch {
            try {
                val result = api.getImages(searchRequest, params.requestedLoadSize, params.key)
                if (result != null) {
                    updateState(State.DONE)
                    callback.onResult(result.imagesResults ?: listOf(), params.requestedLoadSize)
                } else {
                    updateState(State.ERROR)
                }
            } catch (exception: IOException) {
                throw exception
            }
        }
    }

    private fun updateState(newState: State) {
        state.value = newState
    }
}