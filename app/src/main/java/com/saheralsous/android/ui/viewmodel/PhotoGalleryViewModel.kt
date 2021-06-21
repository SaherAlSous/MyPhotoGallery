package com.saheralsous.android.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saheralsous.android.database.repository.GalleryFlowRepositoryImpl
import kotlinx.coroutines.flow.Flow

class PhotoGalleryViewModel(
    private val repositoryImpl : GalleryFlowRepositoryImpl
) : ViewModel(){

    fun getGalleryPaging() : Flow<PagingData<com.saheralsous.android.database.remote.model.PagingData.GalleryItem>> =
        repositoryImpl.fetchPhotos()
            .cachedIn(viewModelScope)
}