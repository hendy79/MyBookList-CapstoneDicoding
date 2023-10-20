package com.hendyapp.mblcore.domain.usecase

import com.hendyapp.mblcore.data.Resource
import com.hendyapp.mblcore.domain.model.BookVolume
import com.hendyapp.mblcore.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookInteractor @Inject constructor(private val bookRepository: BookRepository): BookUseCase {
    override suspend fun searchBooks(query: String): Flow<Resource<List<BookVolume>>> =
        bookRepository.searchBooks(query)

    override suspend fun getFavoriteBooks(): Flow<List<BookVolume>> =
        bookRepository.getFavoriteBooks()

    override suspend fun insertFavoriteBook(book: BookVolume) =
        bookRepository.insertFavoriteBook(book)

    override suspend fun deleteFavoriteBook(book: BookVolume) =
        bookRepository.deleteFavoriteBook(book)
}