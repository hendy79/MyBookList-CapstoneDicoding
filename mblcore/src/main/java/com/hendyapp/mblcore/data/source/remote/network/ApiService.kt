package com.hendyapp.mblcore.data.source.remote.network

import com.hendyapp.mblcore.data.source.remote.response.BookVolumeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("volumes")
    suspend fun searchBooks(@Query("q") query: String): BookVolumeResponse
}