package com.example.foodrecipe.ui.composables.recipeHomepage.ingredientbottomsheet.viewmodel

import com.example.domain.model.IngredientName
import com.example.foodrecipe.ui.base.MviViewModel

data class ViewState(
    val ingredients: List<IngredientName> = emptyList(),
    val isLoading: Boolean = true,
    val hasError: Boolean = false
) : MviViewModel.MviState