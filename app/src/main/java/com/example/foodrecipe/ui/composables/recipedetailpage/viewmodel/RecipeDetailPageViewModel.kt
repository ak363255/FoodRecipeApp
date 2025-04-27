package com.example.foodrecipe.ui.composables.recipedetailpage.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.data.Convertor
import com.example.domain.model.Recipe
import com.example.foodrecipe.ui.base.MviViewModel
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.RecipeMainScreenRoute
import com.example.foodrecipe.ui.composables.recipedetailpage.model.Effect
import com.example.foodrecipe.ui.composables.recipedetailpage.model.ViewEvent
import com.example.foodrecipe.ui.composables.recipedetailpage.model.ViewResult
import com.example.foodrecipe.ui.composables.recipedetailpage.model.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge

class RecipeDetailPageViewModel(
    private val savedStateHandle: SavedStateHandle
) : MviViewModel<ViewEvent, ViewResult, ViewState, Effect>(initialState = ViewState()){

    init {
        processEvent(ViewEvent.GetRecipeDetailPageData)
    }

    private fun getRecipeDetailPageData(): Recipe? {
        val data = Convertor.convertJsonToData<Recipe>(savedStateHandle.toRoute<RecipeMainScreenRoute.RecipeDetailPage>().data)
        return data
    }

    override fun Flow<ViewEvent>.toResult(): Flow<ViewResult> {
       return merge(
           filterIsInstance<ViewEvent.GetRecipeDetailPageData>().getData()
       )
    }

    override fun ViewResult.reduce(
        initialState: ViewState
    ): ViewState {
       return when(val result = this){
           is ViewResult.OnNoResult -> {
               initialState.copy(isLoading = false, isError = true)
           }
           is ViewResult.OnRecipeDetailPageData -> {
               initialState.copy(isLoading = false, isError = false, recipe = result.data)
           }
       }
    }

    override fun Flow<ViewResult>.toEffect(): Flow<Effect> {
       return emptyFlow()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<ViewEvent.GetRecipeDetailPageData>.getData(): Flow<ViewResult>{
        return flatMapLatest {
            val data = getRecipeDetailPageData()
            if(data != null){
                flowOf(ViewResult.OnRecipeDetailPageData(data = data))
            }else{
                flowOf(ViewResult.OnNoResult(msg = "Something went wrong!!"))
            }
        }
    }
}