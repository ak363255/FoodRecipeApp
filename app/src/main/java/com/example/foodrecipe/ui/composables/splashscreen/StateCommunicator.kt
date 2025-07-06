package com.example.foodrecipe.ui.composables.splashscreen

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow

interface StateCommunicator<S> : Communicator<S>{
    class Base<S>(defaultValue : S): StateCommunicator<S>, Communicator.AbstractStateCommunicator<S>(defaultValue)
}

