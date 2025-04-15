package com.example.domain.usecase

import com.example.domain.model.CusineNames
import com.example.domain.model.Result
import com.example.domain.repo.GetRecipeHomeRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCuisineUsecase(
    private val recipeHomeRepo: GetRecipeHomeRepo,
    ioDispatcher: CoroutineDispatcher
) : BaseFlowUseCase<Unit, CusineNames>(ioDispatcher) {
    override fun execute(parameters: Unit): Flow<Result<CusineNames>> = flow {
        emit(recipeHomeRepo.getCuisines())
    }

}