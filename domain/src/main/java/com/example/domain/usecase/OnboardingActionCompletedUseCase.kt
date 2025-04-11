package com.example.domain.usecase

import androidx.datastore.preferences.core.Preferences
import com.example.domain.model.Result
import com.example.domain.repo.DataStoreOperationRepo
import kotlinx.coroutines.CoroutineDispatcher

class OnboardingActionCompletedUseCase(
    private val dataStoreOperationRepo: DataStoreOperationRepo,
    private val dispatcher : CoroutineDispatcher
):BaseUseCase<Preferences.Key<Boolean>,Unit>(dispatcher) {
    override suspend fun execute(parameters: Preferences.Key<Boolean>): Result<Unit> {
        dataStoreOperationRepo.setBooleanToDataStore(parameters,true)
        return Result.Success(Unit)
    }

}