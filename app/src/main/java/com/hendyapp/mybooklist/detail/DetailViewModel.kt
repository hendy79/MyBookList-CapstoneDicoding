package com.hendyapp.mybooklist.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hendyapp.mblcore.domain.model.BookVolume
import com.hendyapp.mblcore.domain.usecase.BookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val bookUseCase: BookUseCase): ViewModel() {
    private val _stateFavoriteEvent: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val stateFavoriteEvent: StateFlow<Boolean> = _stateFavoriteEvent

    fun insertFavoriteBook(book: BookVolume) = viewModelScope.launch {
        bookUseCase.insertFavoriteBook(book)
        _stateFavoriteEvent.value = true
    }

    fun deleteFavoriteBook(book: BookVolume) = viewModelScope.launch {
        bookUseCase.deleteFavoriteBook(book)
        _stateFavoriteEvent.value = false
    }

    fun setStateFavoriteEvent(favorite: Boolean) {
        _stateFavoriteEvent.value = favorite
    }
}