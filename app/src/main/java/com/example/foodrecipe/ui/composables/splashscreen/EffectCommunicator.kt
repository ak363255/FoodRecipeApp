package com.example.foodrecipe.ui.composables.splashscreen

import kotlinx.coroutines.channels.BufferOverflow

interface EffectCommunicator<E> : Communicator<E> {
    class Base<E>(
        replay: Int,
        extraBufferCapacity: Int,
        onBufferOverflow: BufferOverflow
    ) : EffectCommunicator<E>, Communicator.AbstractSharedCommunicator<E>(
        replay = replay,
        extraBufferCapacity = extraBufferCapacity,
        onBufferOverFlow = onBufferOverflow
    )
}