package com.saheralsous.android.database.remote.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class FlickrResponse(
    @SerializedName("photos")
    val photos: Photos,
    @SerializedName("extra")
    val extra: Extra,
    @SerializedName("stat")
    val stat: String
) {
    @Keep
    data class Photos(
        @SerializedName("page")
        val page: Int,
        @SerializedName("pages")
        val pages: Int,
        @SerializedName("perpage")
        val perpage: Int,
        @SerializedName("total")
        val total: Int,
        @SerializedName("photo")
        val photo: List<Photo>
    ) {
        @Keep
        data class Photo(
            @SerializedName("id")
            val id: String,
            @SerializedName("owner")
            val owner: String,
            @SerializedName("secret")
            val secret: String,
            @SerializedName("server")
            val server: String,
            @SerializedName("farm")
            val farm: Int,
            @SerializedName("title")
            val title: String,
            @SerializedName("ispublic")
            val ispublic: Int,
            @SerializedName("isfriend")
            val isfriend: Int,
            @SerializedName("isfamily")
            val isfamily: Int,
            @SerializedName("url_s")
            val urlS: String,
            @SerializedName("height_s")
            val heightS: Int,
            @SerializedName("width_s")
            val widthS: Int
        )
    }

    @Keep
    data class Extra(
        @SerializedName("explore_date")
        val exploreDate: String,
        @SerializedName("next_prelude_interval")
        val nextPreludeInterval: Int
    )
}