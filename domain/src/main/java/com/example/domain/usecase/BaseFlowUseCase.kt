package com.example.domain.usecase

import com.example.domain.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.lang.Exception

abstract class BaseFlowUseCase<in P, out D>(private val dispatcher: CoroutineDispatcher) {
     operator fun invoke(p: P): Flow<Result<D>>  = execute(p).catch{
        emit(Result.Error(it.message?.toString()?:"Error"))
    }.flowOn(dispatcher)

    protected abstract  fun execute(parameters: P): Flow<Result<D>>
}