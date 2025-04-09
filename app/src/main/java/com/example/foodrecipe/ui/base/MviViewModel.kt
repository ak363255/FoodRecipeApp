package com.example.foodrecipe.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class MviViewModel<Event : MviViewModel.MviEvent,
        Result : MviViewModel.MviResult,
        State : MviViewModel.MviState,
        Effect : MviViewModel.MviSideEffect> (initialState: State):
    ViewModel() {
        val states : StateFlow<State>
        val effects : SharedFlow<Effect>
        private val events = MutableSharedFlow<Event>()
        init {
              events
                  .share()
                  .toResult()
                  .share()
                  .also { result ->
                      states = result.toState(initialState)
                          .stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = initialState)
                      effects = result.toEffect().share()
                  }
        }


    fun processEvent(event: Event){
        viewModelScope.launch {
            events.emit(event)
        }
    }
    protected abstract fun Flow<Event>.toResult():Flow<Result>
    protected abstract fun Result.reduce(initialState: State):State
    protected open fun Flow<Result>.toEffect():Flow<Effect> = emptyFlow()
    private fun Flow<Result>.toState(initialState: State):Flow<State>{
        return scan(initialState){prevState,result -> result.reduce(prevState)}
    }
    private fun<T> Flow<T>.share() = shareIn(scope = viewModelScope, started = SharingStarted.Eagerly)

    interface MviEvent
    interface MviResult
    interface MviState
    interface MviSideEffect
}