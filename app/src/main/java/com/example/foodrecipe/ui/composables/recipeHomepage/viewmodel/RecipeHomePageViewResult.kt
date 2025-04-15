package com.example.foodrecipe.ui.composables.recipeHomepage.viewmodel

import com.example.domain.model.CusineName
import com.example.domain.model.Ingredient
import com.example.domain.model.IngredientName
import com.example.domain.model.Recipe
import com.example.domain.model.RecipeList
import com.example.foodrecipe.ui.base.MviViewModel

sealed class RecipeHomePageViewResult : MviViewModel.MviResult {
    data class RecipeHomePageContentResult(
         val recipeList: List<Recipe>,
         val cuisines : List<CusineName>,
         val ingredients : List<IngredientName>
    ): RecipeHomePageViewResult()

    data class OnIngredientSheet( val ingredients: List<IngredientName>): RecipeHomePageViewResult()
    data class OnRecipeDetailPage( val recipe: Recipe): RecipeHomePageViewResult()
    data class OnIngredientSearchPage( val ingredient: Ingredient): RecipeHomePageViewResult()
    data class OnCuisineSearchPage( val cuisine: CusineName): RecipeHomePageViewResult()
    data object NoResultFound:RecipeHomePageViewResult()
}