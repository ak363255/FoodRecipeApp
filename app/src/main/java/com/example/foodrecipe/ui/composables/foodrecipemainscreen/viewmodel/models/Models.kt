package com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models

import com.example.foodrecipe.ui.base.MviViewModel

data class ViewState(
    val showAppOnBoarding: Boolean = false
) : MviViewModel.MviState

sealed interface ViewEvent : MviViewModel.MviEvent {
    data object AppOnboardingStatus : ViewEvent
    data object AppOnboardingCompletedEvent : ViewEvent
}

sealed interface ViewResult : MviViewModel.MviResult {
    data class AppOnboardingShown(val shown: Boolean) : ViewResult
    data object AppOnboardingActionCompleted:ViewResult
}

sealed interface ViewEffect : MviViewModel.MviSideEffect{
    data object OpenRecipeHomePage:ViewEffect
}



