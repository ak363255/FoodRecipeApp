package com.example.domain.repo

import com.example.domain.model.Recipe
import com.example.domain.model.RecipeList
import com.example.domain.model.Result
import com.example.domain.usecase.GetRandomRecipeUseCase

interface GetRandomRecipeRepo {
    suspend fun getRandomRecipes(params: GetRandomRecipeUseCase.GetRandomRecipeUseCaseParams): Result<RecipeList>
}