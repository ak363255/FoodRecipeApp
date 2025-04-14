package com.example.domain.usecase

import com.example.domain.model.RecipeList
import com.example.domain.model.Result
import com.example.domain.repo.GetRandomRecipeRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRandomRecipeUseCase(
    private val repository: GetRandomRecipeRepo,
     ioDispatcher : CoroutineDispatcher
) : BaseFlowUseCase<GetRandomRecipeUseCase.GetRandomRecipeUseCaseParams, RecipeList>(ioDispatcher){
    override fun execute(parameters: GetRandomRecipeUseCaseParams): Flow<Result<RecipeList>> = flow {
        emit(repository.getRandomRecipes(parameters))
    }


    data class GetRandomRecipeUseCaseParams(
        val tags:String,
        val number:Int = 10
    )
}