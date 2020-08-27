package com.yuriy.imageloader.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yuriy.imageloader.database.SavedImagesDao
import com.yuriy.imageloader.entities.ImageResult
import com.yuriy.imageloader.entities.SavedImageInfo
import com.yuriy.imageloader.file_storage.FileStorageManager
import com.yuriy.imageloader.network.api.TenorApiService
import com.yuriy.imageloader.utils.MessageUtils

const val PAGE_SIZE: Int = 10

class ImagesRepository(
    apiService: TenorApiService,
    messageUtils: MessageUtils,
    private val dao: SavedImagesDao,
    private val fileStorage: FileStorageManager
) {

    val netDataSourceFactory: NetDataSourceFactory
    val networkImagesList: LiveData<PagedList<ImageResult>>
    val savedImagesListInfo: LiveData<PagedList<SavedImageInfo>>

    init {

        val netPagingConfig = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .setEnablePlaceholders(false)
            .build()

        netDataSourceFactory =
            NetDataSourceFactory(apiService, messageUtils)
        networkImagesList = LivePagedListBuilder(netDataSourceFactory, netPagingConfig).build()

        val localDataFactory: DataSource.Factory<Int, SavedImageInfo> = dao.getImagesFactory()
        savedImagesListInfo = LivePagedListBuilder(localDataFactory, 10).build()
    }

    fun initSearch(searchRequest: String) {
        netDataSourceFactory.newSearchRequest(searchRequest)
    }

    suspend fun saveImageToFileSystem(imageResult: ImageResult): SavedImageInfo? {
        return fileStorage.saveToDevice(imageResult)
    }

    suspend fun saveImageDataToDatabase(savedImageInfo: SavedImageInfo) {
        dao.insertImage(savedImageInfo)
    }
}