package com.example.foodrecipe.ui.di

import com.example.data.pref.repo.DataStoreOperationRepoImpl
import com.example.domain.repo.DataStoreOperationRepo
import org.koin.dsl.module

val repositorymodule = module {

    single<DataStoreOperationRepo> {
        DataStoreOperationRepoImpl(get())
    }
}