package com.example.foodrecipe.ui.composables.splashscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun <S,E,A,F,CP: ContractProvider<S,E,A,F>>ScreenContent(
    initialState: S,
    contractProvider: CP,
    content:@Composable  ScreenScope<S,E,F>.(S)->Unit
    ) {
    val screenScope = remember { ScreenScope.Base<S,E,A,F>(contractProvider,initialState)}
    content(screenScope,screenScope.fetchState())
}