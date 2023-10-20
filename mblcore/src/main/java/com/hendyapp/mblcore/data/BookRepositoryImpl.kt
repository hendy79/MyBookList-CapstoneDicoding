package com.hendyapp.mblcore.data

import com.hendyapp.mblcore.data.source.local.LocalDataSource
import com.hendyapp.mblcore.data.source.remote.RemoteDataSource
import com.hendyapp.mblcore.data.source.remote.network.ApiResponse
import com.hendyapp.mblcore.domain.model.BookVolume
import com.hendyapp.mblcore.domain.repository.BookRepository
import com.hendyapp.mblcore.utils.mapEntitiesToDomains
import com.hendyapp.mblcore.utils.mapResponsesToDomains
import com.hendyapp.mblcore.utils.mapToEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : BookRepository {
    override suspend fun searchBooks(query: String): Flow<Resource<List<BookVolume>>> =
        remoteDataSource.searchBooks(query).map {
            when(it) {
                is ApiResponse.Success -> {
                    getFavoriteBooks().collect { favs ->
                        it.data.forEach { item ->
                            item.favorite = favs.any { fav -> fav.id == item.id }
                        }
                    }
                    Resource.Success(it.data.mapResponsesToDomains())
                }
                is ApiResponse.Empty -> {
                    Resource.Success(listOf())
                }
                is ApiResponse.Error -> {
                    Resource.Error(it.errorMessage)
                }
            }
        }

    override suspend fun getFavoriteBooks(): Flow<List<BookVolume>> =
        flow {
            emit(localDataSource.getAllFavoriteBooks().mapEntitiesToDomains())
        }.flowOn(Dispatchers.IO)

    override suspend fun insertFavoriteBook(book: BookVolume) =
        localDataSource.insertFavoriteBook(book.mapToEntity())

    override suspend fun deleteFavoriteBook(book: BookVolume) =
        localDataSource.deleteFavoriteBook(book.mapToEntity())
}