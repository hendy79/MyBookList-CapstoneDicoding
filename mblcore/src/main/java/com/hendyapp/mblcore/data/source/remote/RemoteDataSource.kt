package com.hendyapp.mblcore.data.source.remote

import com.hendyapp.mblcore.data.source.remote.network.ApiResponse
import com.hendyapp.mblcore.data.source.remote.network.ApiService
import com.hendyapp.mblcore.data.source.remote.response.BookVolumeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun searchBooks(query: String): Flow<ApiResponse<List<BookVolumeItem>>> =
        flow {
            try {
                val response = apiService.searchBooks(query)
                val books = response.bookVolumeList
                if(books.isNotEmpty())
                    emit(ApiResponse.Success(books))
                else
                    emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
}