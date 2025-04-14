package com.example.foodrecipe.ui.composables.recipeHomepage.viewmodel

import com.example.domain.model.CusineName
import com.example.domain.model.CusineNames
import com.example.domain.model.Ingredient
import com.example.domain.model.IngredientName
import com.example.domain.model.Recipe
import com.example.foodrecipe.ui.base.MviViewModel

sealed class RecipeHomePageViewEvent: MviViewModel.MviEvent {
     data object GetAllRecipeHomePageContent: RecipeHomePageViewEvent()
    data class RecipeItemClick(private val recipe: Recipe): RecipeHomePageViewEvent()
    data class IngredientItemClick(private val ingredient: IngredientName): RecipeHomePageViewEvent()
    data class CuisineItemClick(private val cusineName: CusineName): RecipeHomePageViewEvent()
    data class ViewAllIngredientClick(private val cuisines: List<CusineName>): RecipeHomePageViewEvent()
}