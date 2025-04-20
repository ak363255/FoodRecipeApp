package com.example.foodrecipe.ui.composables.recipeHomepage.ingredientbottomsheet.viewmodel

import com.example.domain.model.IngredientName
import com.example.foodrecipe.ui.base.MviViewModel

sealed interface ViewResult : MviViewModel.MviResult {
    data class OnAllIngredient(val ingredients:List<IngredientName>): ViewResult
    data class GoToSearchScreen(val ingredientName: String): ViewResult
    data object OnNoIngredientFound: ViewResult
}