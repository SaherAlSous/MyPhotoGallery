package com.saheralsous.android.database.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.saheralsous.android.ui.adapter.PhotoPaging
import kotlinx.coroutines.flow.Flow

class GalleryFlowRepositoryImpl(
    private val pagingSource: PhotoPaging
) : GalleryFlowRepository {
    override fun fetchPhotos(): Flow<PagingData<com.saheralsous.android.database.remote.model.PagingData.GalleryItem>> {
        return Pager(
            defaultPagingConfig(),
            pagingSourceFactory = { pagingSource }
        ).flow
    }
    private fun defaultPagingConfig() : PagingConfig{
        return PagingConfig(
            pageSize = 100,
            prefetchDistance = 100,
            enablePlaceholders = false,
            initialLoadSize = 100,
            maxSize = 300
        )
    }
}