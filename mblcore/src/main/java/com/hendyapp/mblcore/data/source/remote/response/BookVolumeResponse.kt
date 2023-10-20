package com.hendyapp.mblcore.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class BookVolumeResponse(
    @SerializedName("items")
    val bookVolumeList: List<BookVolumeItem>
)

data class BookVolumeItem(
    @SerializedName("id")
    val id: String,

    @SerializedName("volumeInfo")
    val bookInfo: BookVolumeInfo,

    var favorite: Boolean = false
)

data class BookVolumeInfo(
    @SerializedName("title")
    var title: String? = null,

    @SerializedName("authors")
    var authors: List<String>? = null,

    @SerializedName("publisher")
    var publisher: String? = null,

    @SerializedName("publishedDate")
    var publishedDate: String? = null,

    @SerializedName("description")
    var desc: String? = null,

    @SerializedName("pageCount")
    var page: Int? = null,

    @SerializedName("imageLinks")
    var imageLink: BookVolumeImage? = null
)

data class BookVolumeImage(
    @SerializedName("smallThumbnail")
    var small: String? = null,

    @SerializedName("thumbnail")
    var normal: String? = null
)
