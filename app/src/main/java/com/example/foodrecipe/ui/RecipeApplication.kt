package com.example.foodrecipe.ui

import android.app.Application
import com.example.foodrecipe.ui.di.AppDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RecipeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RecipeApplication)
            modules(AppDiModule.appDiModules)
        }
    }
}