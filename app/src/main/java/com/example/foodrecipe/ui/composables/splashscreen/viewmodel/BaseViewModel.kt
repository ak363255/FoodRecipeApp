package com.example.foodrecipe.ui.composables.splashscreen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foodrecipe.ui.composables.splashscreen.Actor
import com.example.foodrecipe.ui.composables.splashscreen.BaseAction
import com.example.foodrecipe.ui.composables.splashscreen.BaseEffect
import com.example.foodrecipe.ui.composables.splashscreen.EffectCommunicator
import com.example.foodrecipe.ui.composables.splashscreen.BaseEvent
import com.example.foodrecipe.ui.composables.splashscreen.BaseState
import com.example.foodrecipe.ui.composables.splashscreen.ContractProvider
import com.example.foodrecipe.ui.composables.splashscreen.CoroutineManager
import com.example.foodrecipe.ui.composables.splashscreen.Reducer
import com.example.foodrecipe.ui.composables.splashscreen.StateCommunicator
import com.example.foodrecipe.ui.composables.splashscreen.Store
import kotlinx.coroutines.flow.FlowCollector

abstract class BaseViewModel<S : BaseState, E : BaseEvent, A : BaseAction, F : BaseEffect>(
    val stateCommunicator: StateCommunicator<S>,
    val effectCommunicator: EffectCommunicator<F>
) : ViewModel(), ContractProvider<S, E, A, F>, Actor<S,E,A,F>, Reducer<A, S> {

    private val store = Store.SharedStore<S,E,A,F>(stateCommunicator,effectCommunicator, actor = this, reducer = this, coroutineManager = CoroutineManager.Base())
    override fun dispatchEvent(event: E) {
        store.sendEvent(event)
    }

    override suspend fun collectUiState(collector: FlowCollector<S>) {
        store.collectUiState(collector)
    }

    override suspend fun collectUiEffect(collector: FlowCollector<F>) {
        store.collectUiEffect(collector)
    }

}