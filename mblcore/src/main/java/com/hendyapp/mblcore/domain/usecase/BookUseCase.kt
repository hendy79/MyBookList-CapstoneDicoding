package com.hendyapp.mblcore.domain.usecase

import com.hendyapp.mblcore.data.Resource
import com.hendyapp.mblcore.domain.model.BookVolume
import kotlinx.coroutines.flow.Flow

interface BookUseCase {
    suspend fun searchBooks(query: String): Flow<Resource<List<BookVolume>>>

    suspend fun getFavoriteBooks(): Flow<List<BookVolume>>

    suspend fun insertFavoriteBook(book: BookVolume)

    suspend fun deleteFavoriteBook(book: BookVolume)
}