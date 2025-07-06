package com.example.foodrecipe.ui.composables.splashscreen

import kotlinx.coroutines.flow.FlowCollector

interface BaseEvent
interface BaseAction
interface BaseEffect
interface BaseState

interface BackgroundWorkKey
interface WorkCommand: BackgroundWorkKey

interface ContractProvider<S,E,A,F>{
    fun dispatchEvent(event:E)
    suspend fun collectUiState(collector: FlowCollector<S>)
    suspend fun collectUiEffect(collector: FlowCollector<F>)
}

interface Actor<S,E,A,F>{
    fun WorkScope<S,E,A,F>.handleEvent(e:E)
}
interface Reducer<A,S>{
    fun reduce(action:A,currentState:S):S
}

interface StateProvider<S>{
    suspend fun collectUiState(collector: FlowCollector<S>)
}

interface EffectProvider<F>{
    suspend fun collectUiEffect(collect: FlowCollector<F>)
}

