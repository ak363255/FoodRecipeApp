package com.example.foodrecipe.ui.composables.foodrecipemainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay


@Composable
fun FoodRecipeMainScreen(modifier: Modifier = Modifier, onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1000)
        onFinished()
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Box(modifier = Modifier.padding(it)) { }
    }
}