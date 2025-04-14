package com.example.foodrecipe.ui.composables.recipeHomepage.viewmodel

import com.example.domain.model.CusineName
import com.example.domain.model.Ingredient
import com.example.domain.model.IngredientName
import com.example.domain.model.Recipe
import com.example.domain.model.RecipeList
import com.example.foodrecipe.ui.base.MviViewModel

sealed class RecipeHomePageViewResult : MviViewModel.MviResult {
    data class RecipeHomePageContentResult(
        private val recipeList: List<Recipe>,
        private val cuisines : List<CusineName>,
        private val ingredients : List<IngredientName>
    ): RecipeHomePageViewResult()

    data class OnIngredientSheet(private val ingredients: List<IngredientName>): RecipeHomePageViewResult()
    data class OnRecipeDetailPage(private val recipe: Recipe): RecipeHomePageViewResult()
    data class OnIngredientSearchPage(private val ingredient: Ingredient): RecipeHomePageViewResult()
    data class OnCuisineSearchPage(private val cuisine: CusineName): RecipeHomePageViewResult()
}