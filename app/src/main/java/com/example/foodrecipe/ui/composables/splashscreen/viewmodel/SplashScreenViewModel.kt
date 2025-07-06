package com.example.foodrecipe.ui.composables.splashscreen.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.ui.composables.splashscreen.BaseAction
import com.example.foodrecipe.ui.composables.splashscreen.BaseEffect
import com.example.foodrecipe.ui.composables.splashscreen.BaseEvent
import com.example.foodrecipe.ui.composables.splashscreen.BaseState
import com.example.foodrecipe.ui.composables.splashscreen.EffectCommunicator
import com.example.foodrecipe.ui.composables.splashscreen.StateCommunicator
import com.example.foodrecipe.ui.composables.splashscreen.WorkScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel<S: BaseState,E: BaseEvent,A: BaseAction,F: BaseEffect>(
    stateCommunicator: StateCommunicator<S>,
    effectCommunicator: EffectCommunicator<F>,


    ): BaseViewModel<S, E,A, F>(stateCommunicator,effectCommunicator) {

    private val _uiState : MutableSharedFlow<Boolean> = MutableSharedFlow()
    val uiState : SharedFlow<Boolean>  get() = _uiState

    fun initialize() = viewModelScope.launch {
        //simulate task
        delay(1000)
        _uiState.emit(true)
    }


    override fun reduce(action: A, currentState: S): S {

    }

    override fun WorkScope<S,E,A, F>.handleEvent(e: E) {

    }
}