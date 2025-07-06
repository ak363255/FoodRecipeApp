package com.example.foodrecipe.ui.composables.splashscreen

import kotlinx.coroutines.flow.Flow

sealed class Either<out L,out R>{
    data class Left<L>(val data:L): Either<L, Nothing>()
    data class Right<R>(val data:R): Either<Nothing, R>()
}

typealias ActionResult<A> = Either.Left<A>
typealias EffectResult<F> = Either.Right<F>
typealias WorkResult<A,F> = Either<A,F>
typealias FlowWorkResult<A,F> = Flow<WorkResult<A,F>>
