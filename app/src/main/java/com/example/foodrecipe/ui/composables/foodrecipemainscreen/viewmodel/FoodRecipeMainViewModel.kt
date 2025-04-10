package com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel

import com.example.data.PreferenceKeys
import com.example.domain.model.Result
import com.example.domain.usecase.OnboardingStatusUseCase
import com.example.foodrecipe.ui.base.MviViewModel
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewEffect
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewEvent
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewResult
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class FoodRecipeMainViewModel(
    private val onboardingStatusUseCase: OnboardingStatusUseCase
) : MviViewModel<ViewEvent, ViewResult, ViewState, ViewEffect>(ViewState()) {

    override fun Flow<ViewEvent>.toResult(): Flow<ViewResult> {
        return merge(filterIsInstance<ViewEvent.AppOnboardingStatus>().onBoardingToMainResult())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<ViewEvent.AppOnboardingStatus>.onBoardingToMainResult(): Flow<ViewResult> {
        return flatMapLatest {
            onboardingStatusUseCase(PreferenceKeys.onboarding_completed)
                .map { result ->
                    ViewResult.AppOnboardingShown(shown = (result is Result.Success) && result.data)
                }
        }
    }

    override fun ViewResult.reduce(initialState: ViewState): ViewState {
        return when (val result = this) {
            is ViewResult.AppOnboardingShown -> {
                initialState.copy(showAppOnBoarding = !result.shown)
            }
        }
    }
}