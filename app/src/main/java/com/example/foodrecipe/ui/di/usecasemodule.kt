package com.example.foodrecipe.ui.di

import com.example.domain.usecase.GetRandomRecipeUseCase
import com.example.domain.usecase.OnboardingActionCompletedUseCase
import com.example.domain.usecase.OnboardingStatusUseCase
import org.koin.dsl.module

val usecasemodule = module {
      single {
          OnboardingStatusUseCase(get(),get(IoDispatcher))
      }
    single {
        OnboardingActionCompletedUseCase(get(),get(IoDispatcher))
    }
    single {
        GetRandomRecipeUseCase(get(),get(IoDispatcher))
    }
}