package com.example.foodrecipe.ui.composables.splashscreen

interface  WorkResultHandler<A,F> {
    suspend fun WorkResult<A,F>.handleWork()
    suspend fun FlowWorkResult<A,F>.collectAndHandleWork()
}