package com.example.data.repository

import com.example.data.NetworkResponse
import com.example.data.datamodels.RecipesData
import com.example.data.datamodels.toDomain
import com.example.data.datasource.DataSource
import com.example.data.urls.UrlConstants
import com.example.domain.model.RecipeList
import com.example.domain.model.Result
import com.example.domain.repo.GetRandomRecipeRepo
import com.example.domain.usecase.GetRandomRecipeUseCase

class GetRandomRecipeRepositoryImpl(
    private val remoteDataSource: DataSource.RemoteDataSource
): GetRandomRecipeRepo {
    override suspend fun getRandomRecipes(params: GetRandomRecipeUseCase.GetRandomRecipeUseCaseParams): Result<RecipeList> {
        var url = UrlConstants.RANDOM_RECIPE_API
        if(params.tags.isNotEmpty()){
            url += "&tags=${params.tags}"
        }
        url += "&number=${params.number}"

        val result = remoteDataSource.getRandomRecipes(url)
        return when(result){
            is NetworkResponse.Error -> {
                Result.Error(result.error)
            }
            is NetworkResponse.Success<RecipesData> -> Result.Success(result.data.toDomain())
        }
    }
}