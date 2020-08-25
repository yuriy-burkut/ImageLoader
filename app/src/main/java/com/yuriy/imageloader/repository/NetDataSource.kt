package com.yuriy.imageloader.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.yuriy.imageloader.entities.ImageResult
import com.yuriy.imageloader.network.api.State
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

    val state = MutableLiveData<State>()
    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    var prevPos = 0
    var nextPos = 0

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ImageResult>
    ) {
        updateState(State.LOADING)

        ioScope.launch {
            try {
                val result = api.getImages(searchRequest, params.requestedLoadSize)
                if (result != null) {
                    updateState(State.DONE)
                    nextPos = result.next + 1
                    callback.onResult(result.imageResults ?: listOf(), null, result.next)
                } else {
                    updateState(State.ERROR)
                }
            } catch (exception: IOException) {
                updateState(State.ERROR)
                massageUtils.showError(exception)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ImageResult>) {


        if (getPrevKey(params.key, params.requestedLoadSize) == null) {
            callback.onResult(listOf(), params.requestedLoadSize)
        } else {
            updateState(State.LOADING)

            ioScope.launch {
                try {
                    val result = api.getImages(searchRequest, params.requestedLoadSize)
                    if (result != null) {
                        updateState(State.DONE)
                        callback.onResult(
                            result.imageResults ?: listOf(),
                            getPrevKey(result.next, params.requestedLoadSize)
                        )
                    } else {
                        updateState(State.ERROR)
                    }
                } catch (exception: IOException) {
                    updateState(State.ERROR)
                    massageUtils.showError(exception)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImageResult>) {
        updateState(State.LOADING)

        ioScope.launch {
            try {
                val result = api.getImages(searchRequest, params.requestedLoadSize, params.key)
                if (result != null) {
                    updateState(State.DONE)
                    nextPos = result.next + 1
                    callback.onResult(result.imageResults ?: listOf(), result.next)
                } else {
                    updateState(State.ERROR)
                }
            } catch (exception: IOException) {
                updateState(State.ERROR)
                massageUtils.showError(exception)
            }
        }
    }

    private fun updateState(newState: State) {
        state.postValue(newState)
    }

    private fun getPrevKey(next: Int, loadSize: Int): Int? {
        val prevKey = next - loadSize * 2
        return if (prevKey > 0) {
            prevKey
        } else null
    }
}