package com.saheralsous.android.database.remote.model

import android.net.Uri
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class PagingData(
    val total : Int =0,
    val Page: Int = 0,
    val photos : List<GalleryItem>
){
    val endOfPage = total == Page

    @Keep
    data class GalleryItem(
        @SerializedName("id")
        val id: String,
        @SerializedName("owner")
        val owner: String,
        @SerializedName("title")
        var title: String,
        @SerializedName("url_s")
        val url: String) {
        val photoPageUri : Uri
            get() {
                return Uri.parse("https://www.flickr.com/photos/")
                    .buildUpon()
                    .appendPath(owner)
                    .appendPath(id)
                    .build()
            }
    }
}