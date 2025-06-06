package com.example.foodrecipe.ui.di

import com.example.domain.usecase.GetCuisineUsecase
import com.example.domain.usecase.GetIngredientUsecase
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
    single {
        GetCuisineUsecase(get(),get(IoDispatcher))
    }
    single {
        GetIngredientUsecase(get(),get(IoDispatcher))
    }
}