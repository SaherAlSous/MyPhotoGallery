package com.saheralsous.android.database.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.saheralsous.android.database.remote.model.PagingData as Data

/**
 * creating an interface to implement it inside Pager
 */
interface GalleryFlowRepository {
    fun fetchPhotos():Flow<PagingData<Data.GalleryItem>>
}