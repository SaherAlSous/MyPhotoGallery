package com.saheralsous.android.database.remote

import com.saheralsous.android.database.remote.response.FlickrResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    @GET(Endpoint.API_LINK)
    suspend fun fetchPhotos(
        @Query("page") pageNumber:Int
    ) : FlickrResponse

}