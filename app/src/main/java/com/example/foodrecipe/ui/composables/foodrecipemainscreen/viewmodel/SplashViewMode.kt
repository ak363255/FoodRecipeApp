package com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

sealed class SPLASH_EVENT{
    data object GET_DEVICE_ID : SPLASH_EVENT()
}

class SplashViewMode : ViewModel() {

    val event : MutableSharedFlow<SPLASH_EVENT> = MutableSharedFlow()
    val _state : MutableStateFlow<String> = MutableStateFlow("")
    val state : StateFlow<String> get() = _state
    init {
        event
            .toResult()
            .also {
                it.toState()
            }

    }

    fun Flow<String>.toState() = viewModelScope.launch{
        collect { it ->
            it.toReduce()
        }
    }
    fun String.toReduce() = viewModelScope.launch(Dispatchers.IO){
       _state.value = "DeviceId"
    }

    fun Flow<SPLASH_EVENT>.toResult(): Flow<String>{
        Log.d("SPLASH","to result event ${this}")

        return merge(
            filterIsInstance<SPLASH_EVENT.GET_DEVICE_ID>().toGetDeviceId()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun Flow<SPLASH_EVENT.GET_DEVICE_ID>.toGetDeviceId() : Flow<String>{
        return flatMapLatest {
            getDeviceId()
        }
    }


    fun getDeviceId(): Flow<String> = callbackFlow{
        getDeviceId(callback = {deviceId ->
            trySend(deviceId)
            close()
        })
        awaitClose()
    }

    fun processEvent(splashEvent : SPLASH_EVENT) = viewModelScope.launch{
        Log.d("SPLASH","event ${splashEvent} ${event.subscriptionCount.value}")
        //event.subscriptionCount.filter { it > 0 }.first()
        Log.d("SPLASH","event ${splashEvent} ${event.subscriptionCount.value}")
        event.emit(splashEvent)
    }

    fun initialize() = viewModelScope.launch{
        processEvent(SPLASH_EVENT.GET_DEVICE_ID)
    }




}

fun getDeviceId(callback : (String)-> Unit){
    callback("Success")
}