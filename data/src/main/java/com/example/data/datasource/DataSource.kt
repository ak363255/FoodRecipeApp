package com.example.data.datasource

import com.example.data.NetworkResponse
import com.example.data.datamodels.CusinesDataModelList
import com.example.data.datamodels.IngredientDataModelList
import com.example.data.datamodels.RecipesData

interface DataSource {

    interface LocalDataSource{}
    interface RemoteDataSource{
        suspend fun getRandomRecipes(url: String): NetworkResponse<RecipesData>
        suspend fun getIngredients(url: String) : NetworkResponse<IngredientDataModelList>
        suspend fun getCusines(url: String): NetworkResponse<CusinesDataModelList>
    }

}