package com.example.data.repository

import com.example.data.NetworkResponse
import com.example.data.datamodels.CusinesDataModelList
import com.example.data.datamodels.IngredientDataModelList
import com.example.data.datamodels.RecipesData
import com.example.data.datamodels.toDomain
import com.example.data.datamodels.toDomainModel
import com.example.data.datasource.DataSource
import com.example.data.urls.UrlConstants
import com.example.domain.model.CusineNames
import com.example.domain.model.IngredientNames
import com.example.domain.model.RecipeList
import com.example.domain.model.Result
import com.example.domain.repo.GetRecipeHomeRepo
import com.example.domain.usecase.GetRandomRecipeUseCase

class GetRecipeHomeRepositoryImpl(
    private val remoteDataSource: DataSource.RemoteDataSource
) : GetRecipeHomeRepo {
    override suspend fun getRandomRecipes(params: GetRandomRecipeUseCase.GetRandomRecipeUseCaseParams): Result<RecipeList> {
        var url = UrlConstants.RANDOM_RECIPE_API
        if (params.tags.isNotEmpty()) {
            url += "&tags=${params.tags}"
        }
        url += "&number=${params.number}"

        val result = remoteDataSource.getRandomRecipes(url)
        return when (result) {
            is NetworkResponse.Error -> {
                //Result.Success(RecipeList(recipes = emptyList()))
                Result.Error(result.error)
            }

            is NetworkResponse.Success<RecipesData> -> Result.Success(result.data.toDomain())
        }
    }

    override suspend fun getCuisines(): Result<CusineNames> {
        val response = remoteDataSource.getCusines("")
        return when (response) {
            is NetworkResponse.Error -> Result.Error(response.error)
            is NetworkResponse.Success<CusinesDataModelList> -> Result.Success(response.data.toDomainModel())
        }
    }

    override suspend fun getIngredients(): Result<IngredientNames> {
        val response = remoteDataSource.getIngredients("")
        return when (response) {
            is NetworkResponse.Error -> Result.Error(response.error)
            is NetworkResponse.Success<IngredientDataModelList> -> Result.Success(response.data.toDomainModel())
        }
    }
}