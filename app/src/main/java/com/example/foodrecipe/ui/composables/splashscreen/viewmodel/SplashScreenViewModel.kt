package com.example.foodrecipe.ui.composables.splashscreen.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel: ViewModel() {

    private val _uiState : MutableSharedFlow<Boolean> = MutableSharedFlow()
    val uiState : SharedFlow<Boolean>  get() = _uiState

    fun initialize() = viewModelScope.launch {
        //simulate task
        delay(1000)
        _uiState.emit(true)
    }
}