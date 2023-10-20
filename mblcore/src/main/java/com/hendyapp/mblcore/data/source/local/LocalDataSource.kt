package com.hendyapp.mblcore.data.source.local

import com.hendyapp.mblcore.data.source.local.entity.BookVolumeFavoriteEntity
import com.hendyapp.mblcore.data.source.local.room.BookVolumeFavoriteDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val bookVolumeFavoriteDao: BookVolumeFavoriteDao) {
    suspend fun getAllFavoriteBooks() = bookVolumeFavoriteDao.getAllFavoriteBooks()

    suspend fun insertFavoriteBook(book: BookVolumeFavoriteEntity) = bookVolumeFavoriteDao.insertFavoriteBook(book)

    suspend fun deleteFavoriteBook(book: BookVolumeFavoriteEntity) = bookVolumeFavoriteDao.deleteFavoriteBook(book)
}