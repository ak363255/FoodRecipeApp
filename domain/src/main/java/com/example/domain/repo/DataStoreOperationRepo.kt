package com.example.domain.repo

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import com.example.domain.model.Result


interface DataStoreOperationRepo {
     fun getStringDataStoreValue(key: Preferences.Key<String>):Flow<Result<String>>
     fun getDoubleDataStoreValue(key: Preferences.Key<Double>):Flow<Result<Double>>
     fun getBooleanDataStoreValue(key: Preferences.Key<Boolean>):Flow<Result<Boolean>>
     fun getLongDataStoreValue(key: Preferences.Key<Long>):Flow<Result<Long>>
     fun getIntDataStoreValue(key: Preferences.Key<Int>):Flow<Result<Int>>

    suspend fun setStringToDataStore(key: Preferences.Key<String>, value: String)
    suspend fun setDoubleToDataStore(key: Preferences.Key<Double>, value: Double)
    suspend fun setBooleanToDataStore(key: Preferences.Key<Boolean>, value: Boolean)
    suspend fun setLongToDataStore(key: Preferences.Key<Long>, value: Long)
    suspend fun setIntToDataStore(key: Preferences.Key<Int>, value: Int)
}