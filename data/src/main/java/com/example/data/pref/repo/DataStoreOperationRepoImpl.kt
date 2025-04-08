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

    private fun <T> getValue(key: Preferences.Key<T>): Flow<Result<T>> {
        return dataStore.data.map {
            val value = it[key]
            value?.let { Result.Success(it) } ?: Result.Error("")
        }
    }

    private suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit {
            it[key] = value
        }
    }

    override  fun getValue(key: Preferences.Key<String>): Flow<Result<String>> {
        return getValue<String>(key)
    }

    override  fun getValue(key: Preferences.Key<Double>): Flow<Result<Double>> {
        return getValue<Double>(key)
    }

    override  fun getValue(key: Preferences.Key<Boolean>): Flow<Result<Boolean>> {
        return getValue<Boolean>(key)
    }

    override  fun getValue(key: Preferences.Key<Long>): Flow<Result<Long>> {
        return getValue<Long>(key)
    }

    override  fun getValue(key: Preferences.Key<Int>): Flow<Result<Int>> {
        return getValue<Int>(key)
    }

    override suspend fun setValue(
        key: Preferences.Key<String>,
        value: String
    ) {
        setValue<String>(key, value)
    }

    override suspend fun setValue(
        key: Preferences.Key<Double>,
        value: Double
    ) {
        setValue<Double>(key, value)
    }

    override suspend fun setValue(
        key: Preferences.Key<Boolean>,
        value: Boolean
    ) {
        setValue<Boolean>(key, value)
    }

    override suspend fun setValue(
        key: Preferences.Key<Long>,
        value: Long
    ) {
        setValue<Long>(key, value)
    }

    override suspend fun setValue(
        key: Preferences.Key<Int>,
        value: Int
    ) {
        setValue<Int>(key, value)
    }


}