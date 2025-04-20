package com.example.foodrecipe.ui.composables.recipeHomepage.ingredientbottomsheet.viewmodel

import com.example.foodrecipe.ui.base.MviViewModel

sealed interface Effects : MviViewModel.MviSideEffect{
    data class OpenIngredientSearchPage(val name: String): Effects
}