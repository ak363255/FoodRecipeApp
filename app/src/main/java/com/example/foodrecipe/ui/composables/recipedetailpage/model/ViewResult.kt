package com.example.foodrecipe.ui.composables.recipedetailpage.model

import com.example.domain.model.Recipe
import com.example.foodrecipe.ui.base.MviViewModel

sealed interface ViewResult : MviViewModel.MviResult {
    data class OnRecipeDetailPageData(val data: Recipe): ViewResult
    data class OnNoResult(val msg: String): ViewResult
}