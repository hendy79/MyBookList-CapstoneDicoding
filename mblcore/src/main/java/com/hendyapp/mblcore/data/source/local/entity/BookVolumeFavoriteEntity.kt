package com.hendyapp.mblcore.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookvolumefavorite")
data class BookVolumeFavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var desc: String,

    @ColumnInfo(name = "authors")
    var authors: String,

    @ColumnInfo(name = "publisher")
    var publisher: String,

    @ColumnInfo(name = "publisheddate")
    var publishedDate: String,

    @ColumnInfo(name = "pagecount")
    var pageCount: Int,

    @ColumnInfo(name = "image")
    var image: String
)