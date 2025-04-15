package com.example.foodrecipe.ui.composables.recipeHomepage.viewmodel

import com.example.domain.model.CusineName
import com.example.domain.model.IngredientName
import com.example.domain.model.IngredientNames
import com.example.domain.model.Recipe
import com.example.foodrecipe.ui.base.MviViewModel
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewEffect

sealed class RecipeHomePageEffects : MviViewModel.MviSideEffect{
    data class OpenCuisineSearchPage(val cusine: CusineName): RecipeHomePageEffects()
    data class OpenIngredientSheet(val ingredientNames: List<IngredientName>): RecipeHomePageEffects()
    data object OpenIngredientSearchPage : RecipeHomePageEffects()
    data class OpenRecipeDetailPage(val recipe: Recipe): RecipeHomePageEffects()
}