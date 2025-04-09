package com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models

import com.example.foodrecipe.ui.base.MviViewModel

 data class ViewState(
    val showAppOnBoarding: Boolean = false
) : MviViewModel.MviState

 sealed interface ViewEvent : MviViewModel.MviEvent {
    data object AppOnboardingStatus : ViewEvent
}

 sealed interface ViewResult : MviViewModel.MviResult {
    data class AppOnboardingShown(val shown: Boolean) : ViewResult
}

 sealed interface ViewEffect : MviViewModel.MviSideEffect



