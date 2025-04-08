package com.example.domain.usecase

import com.example.domain.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

abstract class BaseUseCase<in P, out D>(private val dispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(p: P): Result<D> {
        try {
            return withContext(dispatcher) { execute(p) }
        } catch (e: Exception) {
            return Result.Error(e.message?.toString() ?: "")
        }
    }

    protected abstract suspend fun execute(parameters: P): Result<D>
}