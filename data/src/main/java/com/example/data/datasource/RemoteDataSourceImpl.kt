package com.example.data.datasource

import com.example.data.NetworkResponse
import com.example.data.datamodels.CusinesDataModelList
import com.example.data.datamodels.IngredientDataModelList
import com.example.data.datamodels.RecipesData
import com.example.data.datamodels.getDummyCusines
import com.example.data.datamodels.getDummyIngredients
import com.example.data.network.NetworkHelper

class RemoteDataSourceImpl(
    private val apiHelper: NetworkHelper
): DataSource.RemoteDataSource {
    override suspend fun getRandomRecipes(url: String): NetworkResponse<RecipesData> {
         return apiHelper.makeGetRequest(url)
    }

    override suspend fun getIngredients(url: String): NetworkResponse<IngredientDataModelList> {
        return NetworkResponse.Success(getDummyIngredients())
    }

    override suspend fun getCusines(url: String): NetworkResponse<CusinesDataModelList> {
        return NetworkResponse.Success(getDummyCusines())
    }
}