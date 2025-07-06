package com.example.foodrecipe.ui.composables.splashscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope

interface ScreenScope<S,E,F> {
    @Composable
    fun fetchState():S
    @Composable
    fun handleEffect(effect: CoroutineScope.(F)-> Unit)
    fun dispatchEvent(e:E)
    class Base<S,E,A,F>(
        val contractProvider: ContractProvider<S,E,A,F>,
        val initialState:S
    ): ScreenScope<S,E,F>{
        @Composable
        override fun fetchState(): S {
           val state = remember { mutableStateOf(initialState) }
            LaunchedEffect(Unit) {
                contractProvider.collectUiState {
                    state.value = it
                }
            }
            return state.value
        }

        @Composable
        override fun handleEffect(block: CoroutineScope.(F)-> Unit) {
            LaunchedEffect(Unit) {
                contractProvider.collectUiEffect {
                    block(it)
                }
            }
        }

        override fun dispatchEvent(e: E) {
            contractProvider.dispatchEvent(e)
        }

    }
}