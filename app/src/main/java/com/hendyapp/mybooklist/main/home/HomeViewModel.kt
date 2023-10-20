package com.hendyapp.mybooklist.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hendyapp.mblcore.data.Resource
import com.hendyapp.mblcore.domain.model.BookVolume
import com.hendyapp.mblcore.domain.usecase.BookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val bookUseCase: BookUseCase) : ViewModel() {
    private val _stateBookResource: MutableStateFlow<Resource<List<BookVolume>>?> = MutableStateFlow(null)
    val stateBookResource: StateFlow<Resource<List<BookVolume>>?> = _stateBookResource

    private val _stateSearch: MutableStateFlow<String?> = MutableStateFlow(null)
    val stateSearch: StateFlow<String?> = _stateSearch

    fun searchBooks(query: String) = viewModelScope.launch {
        bookUseCase.searchBooks(query).collect {
            _stateBookResource.value = it
        }
    }

    fun setTextSearch(query: String) {
        _stateSearch.value = query
    }
}