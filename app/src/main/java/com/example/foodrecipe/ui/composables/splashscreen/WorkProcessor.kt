package com.example.foodrecipe.ui.composables.splashscreen

interface WorkProcessor<C: WorkCommand,A,F> {
    fun work(command:C): WorkResult<A,F>
}
