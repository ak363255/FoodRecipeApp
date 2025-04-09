package com.example.foodrecipe.ui.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import org.koin.dsl.module

object IoDispatcher : Qualifier {
    override val value: QualifierValue
        get() = "IO"
}
object DefaultDispatcher : Qualifier{
    override val value: QualifierValue
        get() = "Default"

}
object MainDispatcher : Qualifier{
    override val value: QualifierValue
        get() = "Main"
}

val dispatchersmodule  = module {
    single <CoroutineDispatcher>(qualifier = IoDispatcher){
        Dispatchers.IO
    }
    single <CoroutineDispatcher>(qualifier = MainDispatcher){
        Dispatchers.IO
    }
    single <CoroutineDispatcher>(qualifier = DefaultDispatcher){
        Dispatchers.IO
    }
}