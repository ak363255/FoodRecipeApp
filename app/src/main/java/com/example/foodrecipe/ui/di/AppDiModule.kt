package com.example.foodrecipe.ui.di

object AppDiModule {
    val appDiModules = listOf(
        datasourcemodule,
        dbmodule,
        networkmodule,
        repositorymodule,
        usecasemodule,
        viewmodelmoudule,
        dispatchersmodule
    )
}