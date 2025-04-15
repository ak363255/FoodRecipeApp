package com.example.domain.usecase

import com.example.domain.model.IngredientNames
import com.example.domain.model.Result
import com.example.domain.repo.GetRecipeHomeRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetIngredientUsecase(
    private val recipeHomeRepo:GetRecipeHomeRepo,
    ioDispatcher : CoroutineDispatcher
) :BaseFlowUseCase<Unit,IngredientNames>(ioDispatcher){
    override fun execute(parameters: Unit): Flow<Result<IngredientNames>> = flow {
        emit(recipeHomeRepo.getIngredients())
    }

}