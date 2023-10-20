package com.hendyapp.mblcore.utils

import com.hendyapp.mblcore.data.source.local.entity.BookVolumeFavoriteEntity
import com.hendyapp.mblcore.data.source.remote.response.BookVolumeItem
import com.hendyapp.mblcore.domain.model.BookVolume

fun List<BookVolumeItem>.mapResponsesToDomains(): List<BookVolume> =
    map { item ->
        BookVolume(
            item.id,
            item.bookInfo.title ?: "",
            item.bookInfo.desc ?: "",
            item.bookInfo.authors?.joinToString("\n")?.trim() ?: "",
            item.bookInfo.publisher ?: "",
            item.bookInfo.publishedDate ?: "",
            item.bookInfo.page ?: 0,
            item.bookInfo.imageLink?.small?.replace("http:", "https:") ?: "",
            item.bookInfo.imageLink?.normal?.replace("http:", "https:") ?: "",
            item.favorite
        )
    }

fun List<BookVolumeFavoriteEntity>.mapEntitiesToDomains(): List<BookVolume> =
    map { item ->
        BookVolume(
            item.id,
            item.title,
            item.desc,
            item.authors,
            item.publisher,
            item.publishedDate,
            item.pageCount,
            "",
            item.image,
            true
        )
    }

fun BookVolume.mapToEntity(): BookVolumeFavoriteEntity =
    BookVolumeFavoriteEntity(
        this.id,
        this.title,
        this.desc,
        this.authors,
        this.publisher,
        this.publishedDate,
        this.pagecount,
        this.image
    )