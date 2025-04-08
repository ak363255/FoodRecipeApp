package com.example.domain.repo

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import com.example.domain.model.Result


interface DataStoreOperationRepo {
     fun getValue(key: Preferences.Key<String>):Flow<Result<String>>
     fun getValue(key: Preferences.Key<Double>):Flow<Result<Double>>
     fun getValue(key: Preferences.Key<Boolean>):Flow<Result<Boolean>>
     fun getValue(key: Preferences.Key<Long>):Flow<Result<Long>>
     fun getValue(key: Preferences.Key<Int>):Flow<Result<Int>>

    suspend fun setValue(key: Preferences.Key<String>, value: String)
    suspend fun setValue(key: Preferences.Key<Double>, value: Double)
    suspend fun setValue(key: Preferences.Key<Boolean>, value: Boolean)
    suspend fun setValue(key: Preferences.Key<Long>, value: Long)
    suspend fun setValue(key: Preferences.Key<Int>, value: Int)
}