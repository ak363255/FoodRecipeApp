package com.example.foodrecipe.ui.di

import com.example.foodrecipe.ui.composables.apponboarding.viewmodel.AppOnboardingViewModel
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.FoodRecipeMainViewModel
import com.example.foodrecipe.ui.composables.splashscreen.viewmodel.SplashScreenViewModel
import com.example.foodrecipe.ui.composables.recipeHomepage.viewmodel.RecipeHomePageViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewmodelmoudule = module {
    viewModelOf(::FoodRecipeMainViewModel)
    viewModelOf(::AppOnboardingViewModel)
    viewModelOf(::SplashScreenViewModel)
    viewModelOf(::RecipeHomePageViewModel)
}