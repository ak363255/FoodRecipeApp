package com.example.foodrecipe.ui.composables.recipeHomepage.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.GetRandomRecipeUseCase
import com.example.foodrecipe.ui.base.MviViewModel
import kotlinx.coroutines.flow.Flow

class RecipeHomePageViewModel(
    private val getRandomRecipeUseCase: GetRandomRecipeUseCase
): MviViewModel<RecipeHomePageViewEvent, RecipeHomePageViewResult, RecipeHomePageViewState, RecipeHomePageEffects>(
    RecipeHomePageViewState()
) {
    override fun Flow<RecipeHomePageViewEvent>.toResult(): Flow<RecipeHomePageViewResult> {

    }

    override fun RecipeHomePageViewResult.reduce(
        initialState: RecipeHomePageViewState
    ): RecipeHomePageViewState {
    }

    override fun Flow<RecipeHomePageViewResult>.toEffect(): Flow<RecipeHomePageEffects> {
    }
}