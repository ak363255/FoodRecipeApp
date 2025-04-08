package com.example.foodrecipe.ui.di

object AppDiModule {
    val appDiModules = arrayOf(
        datasourcemodule,
        dbmodule,
        networkmodule,
        repositorymodule,
        usecasemodule,
        viewmodelmoudule
    )
}