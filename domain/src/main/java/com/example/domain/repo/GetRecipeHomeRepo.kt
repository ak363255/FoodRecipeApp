package com.example.domain.repo

import com.example.domain.model.CusineNames
import com.example.domain.model.IngredientNames
import com.example.domain.model.RecipeList
import com.example.domain.model.Result
import com.example.domain.usecase.GetRandomRecipeUseCase

interface GetRecipeHomeRepo {
    suspend fun getRandomRecipes(params: GetRandomRecipeUseCase.GetRandomRecipeUseCaseParams): Result<RecipeList>
    suspend fun getCuisines():Result<CusineNames>
    suspend fun getIngredients():Result<IngredientNames>
}