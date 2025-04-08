package com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel

import com.example.foodrecipe.ui.base.MviViewModel
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewEffect
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewEvent
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewResult
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewState
import kotlinx.coroutines.flow.Flow

class FoodRecipeMainViewModel(): MviViewModel<ViewEvent,ViewResult,ViewState,ViewEffect>(ViewState()) {
    override fun Flow<ViewEvent>.toResult(): Flow<ViewResult> {

    }

    override fun ViewResult.reduce(initialState: ViewState): ViewState {

    }
}