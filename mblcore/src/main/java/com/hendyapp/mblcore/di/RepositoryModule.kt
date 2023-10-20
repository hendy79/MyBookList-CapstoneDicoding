package com.hendyapp.mblcore.di

import com.hendyapp.mblcore.data.BookRepositoryImpl
import com.hendyapp.mblcore.domain.repository.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideBookRepository(bookRepository: BookRepositoryImpl): BookRepository
}