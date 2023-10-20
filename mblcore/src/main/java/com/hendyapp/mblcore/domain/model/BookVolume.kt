package com.hendyapp.mblcore.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookVolume(
    val id: String,
    val title: String,
    val desc: String,
    val authors: String,
    val publisher: String,
    val publishedDate: String,
    val pagecount: Int,
    val smallImage: String,
    val image: String,
    val favorite: Boolean
): Parcelable
