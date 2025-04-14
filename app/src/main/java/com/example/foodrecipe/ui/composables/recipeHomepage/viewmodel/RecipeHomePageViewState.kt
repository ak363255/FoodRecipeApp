package com.example.foodrecipe.ui.composables.recipeHomepage.viewmodel

import com.example.domain.model.CusineName
import com.example.domain.model.IngredientName
import com.example.domain.model.Recipe
import com.example.foodrecipe.ui.base.MviViewModel

data class RecipeHomePageViewState(
    val isLoading: Boolean = true,
    val hasError: Boolean = false,
    val randomRecipes: List<Recipe> = emptyList(),
    val cuisines : List<CusineName> = emptyList(),
    val ingredients : List<IngredientName> = emptyList()
) : MviViewModel.MviState