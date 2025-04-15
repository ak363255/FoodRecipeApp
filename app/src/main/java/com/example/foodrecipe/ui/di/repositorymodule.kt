package com.example.foodrecipe.ui.di

import com.example.data.pref.repo.DataStoreOperationRepoImpl
import com.example.data.repository.GetRecipeHomeRepositoryImpl
import com.example.domain.repo.DataStoreOperationRepo
import com.example.domain.repo.GetRecipeHomeRepo
import org.koin.dsl.module

val repositorymodule = module {

    single<DataStoreOperationRepo> {
        DataStoreOperationRepoImpl(get())
    }

    single<GetRecipeHomeRepo> {
        GetRecipeHomeRepositoryImpl(get())
    }
}