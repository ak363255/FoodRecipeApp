package com.example.domain.usecase

import androidx.datastore.preferences.core.Preferences
import com.example.domain.model.Result
import com.example.domain.repo.DataStoreOperationRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class OnboardingStatusUseCase(
    private val dataStoreOperationRepo: DataStoreOperationRepo,
    ioDispatcher: CoroutineDispatcher
) : BaseFlowUseCase<Preferences.Key<Boolean>, Boolean>(ioDispatcher) {
    override  fun execute(parameters: Preferences.Key<Boolean>): Flow<Result<Boolean>> = dataStoreOperationRepo.getBooleanDataStoreValue(parameters)
}