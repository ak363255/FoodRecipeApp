package com.example.data.pref.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.domain.model.Result
import com.example.domain.repo.DataStoreOperationRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreOperationRepoImpl(
    private val dataStore: DataStore<Preferences>
) : DataStoreOperationRepo {

    private fun <T> getDataStoreValue(key: Preferences.Key<T>): Flow<Result<T>> {
        return dataStore.data.map {
            val value = it[key]
            value?.let { Result.Success(it) } ?: Result.Error("")
        }
    }

    private suspend fun <T> setToDataStore(key: Preferences.Key<T>, value: T) {
        dataStore.edit {
            it[key] = value
        }
    }

    override fun getStringDataStoreValue(key: Preferences.Key<String>): Flow<Result<String>> {
        return getDataStoreValue<String>(key)
    }

    override fun getDoubleDataStoreValue(key: Preferences.Key<Double>): Flow<Result<Double>> {
        return getDataStoreValue<Double>(key)
    }

    override fun getBooleanDataStoreValue(key: Preferences.Key<Boolean>): Flow<Result<Boolean>> {
        return getDataStoreValue<Boolean>(key)
    }

    override fun getLongDataStoreValue(key: Preferences.Key<Long>): Flow<Result<Long>> {
        return getDataStoreValue<Long>(key)
    }

    override fun getIntDataStoreValue(key: Preferences.Key<Int>): Flow<Result<Int>> {
        return getDataStoreValue<Int>(key)
    }


    override suspend fun setStringToDataStore(
        key: Preferences.Key<String>,
        value: String
    ) {
        setToDataStore<String>(key, value)
    }

    override suspend fun setDoubleToDataStore(
        key: Preferences.Key<Double>,
        value: Double
    ) {
        setToDataStore<Double>(key, value)
    }

    override suspend fun setBooleanToDataStore(
        key: Preferences.Key<Boolean>,
        value: Boolean
    ) {
        setToDataStore<Boolean>(key, value)
    }

    override suspend fun setLongToDataStore(
        key: Preferences.Key<Long>,
        value: Long
    ) {
        setToDataStore<Long>(key, value)
    }

    override suspend fun setIntToDataStore(
        key: Preferences.Key<Int>,
        value: Int
    ) {
        setToDataStore<Int>(key, value)
    }


}