package com.hendyapp.mblcore.di

import com.hendyapp.mblcore.BuildConfig
import com.hendyapp.mblcore.data.source.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val urlApi = URL(BuildConfig.URL_API)
        val certificatePinner = CertificatePinner.Builder()
            .add(urlApi.host, "sha256/iU5IpXAy6kn6NJ9oeps0Uit6lL2KQmQ8P768RzpCjKc=")
            .add(urlApi.host, "sha256/yR/sBHOK7KjPFiPNHs58psN2futPxbN53rGkrDfxmO4=")
            .add(urlApi.host, "sha256/AZUYFWzqQHBmJa0d65I0EYO5hMwW4CFiqMxTig8KqIw=")
            .build()
        return OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .addInterceptor(HttpLoggingInterceptor()
                .setLevel(if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}