package com.saheralsous.android.ui.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saheralsous.android.database.remote.NetworkApi
import com.saheralsous.android.database.remote.model.PagingData
import com.saheralsous.android.database.remote.model.PagingResponseMapper
import com.saheralsous.android.database.remote.response.FlickrResponse
import kotlin.Exception

class PhotoPaging(
    private val api: NetworkApi) : PagingSource<Int, PagingData.GalleryItem>(),
    PagingResponseMapper<FlickrResponse, PagingData> {

    override val keyReuseSupported: Boolean = true

    override fun getRefreshKey(state: PagingState<Int, PagingData.GalleryItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagingData.GalleryItem> {
        val page= params.key?: 1

        return try {

            api.fetchPhotos(page).run {
                val data = mapFromResponse(this)
                return LoadResult.Page(
                    data = data.photos,
                    prevKey = if (page == 1) null else page.minus(1),
                    nextKey = if (page == 5) null else page.plus(1),
                    )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun mapFromResponse(response: FlickrResponse): PagingData {
        return with(response){
            PagingData(
                total = photos.pages,
                Page = photos.page,
                photos =photos.photo.map {
                    PagingData.GalleryItem(
                        id = it.id,
                        owner = it.owner,
                        url = it.urlS,
                        title = it.title
                    )
                }
            )
        }
    }
}
