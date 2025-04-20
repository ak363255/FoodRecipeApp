package com.example.foodrecipe.ui.composables.recipeHomepage.ingredientbottomsheet.viewmodel

import com.example.foodrecipe.ui.base.MviViewModel

sealed interface ViewEvent: MviViewModel.MviEvent{
    data object GetAllIngredientEvent: ViewEvent
    data class IngredientClickEvent(val name: String): ViewEvent
}