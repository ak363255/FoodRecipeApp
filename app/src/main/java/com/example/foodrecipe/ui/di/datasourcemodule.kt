package com.example.foodrecipe.ui.di

import com.example.data.datasource.DataSource
import com.example.data.datasource.RemoteDataSourceImpl
import com.example.data.pref.repo.DataStoreOperationRepoImpl
import com.example.domain.repo.DataStoreOperationRepo
import org.koin.dsl.module

val datasourcemodule = module {

    single<DataStoreOperationRepo> {
        DataStoreOperationRepoImpl(get())
    }
    single<DataSource.RemoteDataSource> {
        RemoteDataSourceImpl(get())
    }

}