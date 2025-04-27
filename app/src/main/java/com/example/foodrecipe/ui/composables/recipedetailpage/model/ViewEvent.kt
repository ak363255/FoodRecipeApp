package com.example.foodrecipe.ui.composables.recipedetailpage.model

import com.example.foodrecipe.ui.base.MviViewModel

sealed interface ViewEvent : MviViewModel.MviEvent{
    data object GetRecipeDetailPageData: ViewEvent
}