package com.example.foodrecipe.ui.di

import com.example.domain.usecase.OnboardingStatusUseCase
import org.koin.dsl.module

val usecasemodule = module {
      single {
          OnboardingStatusUseCase(get(),get(IoDispatcher))
      }
}