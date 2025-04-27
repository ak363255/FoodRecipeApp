package com.example.foodrecipe.ui.composables.recipedetailpage.model

import com.example.domain.model.Recipe
import com.example.foodrecipe.ui.base.MviViewModel

data class ViewState(
    val recipe: Recipe? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false

): MviViewModel.MviState