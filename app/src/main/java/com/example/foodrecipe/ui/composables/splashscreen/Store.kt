package com.example.foodrecipe.ui.composables.splashscreen

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.isActive

interface Store<S, E, A, F> : StateProvider<S>, EffectProvider<F> {

    fun sendEvent(event: E)
    fun reduce(action: A, currentState: S): S
    suspend fun postEffect(e:F)
    suspend fun handleAction(action:A)
    suspend fun fetchState():S

    abstract class BaseStore<S, E, A, F>(
        private val stateCommunicator: StateCommunicator<S>,
        private val effectCommunicator: EffectCommunicator<F>,
        val actor: Actor<S,E,A,F>,
        val reducer: Reducer<A, S>,
        val coroutineManager: CoroutineManager
    ) : Store<S, E, A, F> {
        private val eventChannel = Channel<E>(Channel.UNLIMITED, BufferOverflow.SUSPEND)

        fun start(scope: CoroutineScope) {
           val workScope: WorkScope<S,E,A,F> = WorkScope.Base<S,E,A,F>(this,scope)
            coroutineManager.runOnBackground(scope) {
                while(isActive){
                    val event:E = eventChannel.receive()
                    with(actor){
                        workScope.handleEvent(event)
                    }
                }
            }
        }

        override fun sendEvent(event: E) {
            eventChannel.trySend(event)
        }

        override fun reduce(action: A, currentState: S): S {
            return reducer.reduce(action, currentState)
        }

        override suspend fun collectUiState(collector: FlowCollector<S>) {
            stateCommunicator.collectState(collector)
        }

        override suspend fun handleAction(action: A) {
            reducer.reduce(action,fetchState())
        }

        override  suspend fun fetchState(): S  = stateCommunicator.currentState()



    }

    class SharedStore<S,E,A,F>(
        private val stateCommunicator: StateCommunicator<S>,
        private val effectCommunicator: EffectCommunicator<F>,
        actor: Actor<S,E,A,F>,
        reducer: Reducer<A,S>,
        coroutineManager: CoroutineManager
    ): Store<S,E,A,F>,
        BaseStore<S,E,A,F>(stateCommunicator,effectCommunicator,actor,reducer,coroutineManager){
        override suspend fun collectUiEffect(collect: FlowCollector<F>) {
             effectCommunicator.collectState(collect)
        }

        override suspend fun postEffect(e: F) {
            effectCommunicator.updateState(e)
        }

    }
}