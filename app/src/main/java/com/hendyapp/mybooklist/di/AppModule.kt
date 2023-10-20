package com.hendyapp.mybooklist.di

import com.hendyapp.mblcore.domain.usecase.BookInteractor
import com.hendyapp.mblcore.domain.usecase.BookUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract fun provideTourismUseCase(tourismInteractor: BookInteractor): BookUseCase
}