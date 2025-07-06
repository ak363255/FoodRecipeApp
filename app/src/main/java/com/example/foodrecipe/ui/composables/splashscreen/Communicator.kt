package com.example.foodrecipe.ui.composables.splashscreen

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

interface Communicator<S> {
    suspend fun collectState(collector: FlowCollector<S>)
    suspend fun currentState(): S
    fun updateState(state: S)

    abstract class AbstractStateCommunicator<S>(defaultValue: S) : Communicator<S> {
        private var state: MutableStateFlow<S> = MutableStateFlow(defaultValue)
        override suspend fun currentState(): S = state.value
        override fun updateState(state: S) {
            this.state.value = state
        }

        override suspend fun collectState(collector: FlowCollector<S>) {
            state.collect(collector)
        }
    }

    abstract class AbstractSharedCommunicator<E>(
        replay: Int,
        extraBufferCapacity: Int,
        onBufferOverFlow: BufferOverflow
    ) : Communicator<E> {
        private var effect: MutableSharedFlow<E> = MutableSharedFlow(
            replay = replay,
            extraBufferCapacity = extraBufferCapacity,
            onBufferOverflow = onBufferOverFlow
        )

        override suspend fun currentState(): E  = effect.first()
        override fun updateState(state: E) {
            effect.tryEmit(state)
        }

        override suspend fun collectState(collector: FlowCollector<E>) {
            effect.collect(collector)
        }
    }
}