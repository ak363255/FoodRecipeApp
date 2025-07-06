package com.example.foodrecipe.ui.composables.splashscreen

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface WorkScope<S,E,A, F> : WorkResultHandler<A, F> {
    fun launchBackgroundWork(
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        block: CoroutineBlock,
        command: BackgroundWorkKey
    ): Job

    suspend   fun sendAction(action: A)
    suspend  fun sendEffect(effect: F)
    suspend fun state():S
    class Base<S, E,A, F>(
        private val store: Store.BaseStore<S,E, A, F>,
        private val coroutineScope: CoroutineScope
    ) : WorkScope<S,E,A, F> {
        val backgroundWorkMap = mutableMapOf<BackgroundWorkKey, Job>()
        override fun launchBackgroundWork(
            scope: CoroutineScope,
            dispatcher: CoroutineDispatcher,
            block: CoroutineBlock,
            command: BackgroundWorkKey
        ): Job {
            if (backgroundWorkMap.contains(command)) {
                backgroundWorkMap[command]?.cancel()
                backgroundWorkMap.remove(command)
            }
            return scope.launch(dispatcher, block = block).apply {
                start()
                backgroundWorkMap[command] = this
            }
        }

        override suspend fun sendAction(action: A) {
            store.handleAction(action)
        }

        override suspend fun sendEffect(effect: F) {
            store.postEffect(effect)
        }

        override suspend  fun state(): S = store.fetchState()


        override suspend fun WorkResult<A, F>.handleWork(

        ) {
            when (this) {
                is Either.Left<A> -> {
                    sendAction(data)
                }

                is Either.Right<F> -> {
                    sendEffect(data)
                }
            }
        }

        override suspend fun FlowWorkResult<A, F>.collectAndHandleWork(
        ) {
            collect { it.handleWork() }
        }
    }

}