package com.hendyapp.mybooklist.main

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
class MainViewModel @Inject constructor(private val bookUseCase: BookUseCase) : ViewModel() {
    private val _stateBookFavorites: MutableStateFlow<List<BookVolume>?> = MutableStateFlow(null)
    val stateBookFavorite: StateFlow<List<BookVolume>?> = _stateBookFavorites

    fun getFavoriteBooks() = viewModelScope.launch {
        bookUseCase.getFavoriteBooks().collect {
            _stateBookFavorites.value = it
        }
    }
}