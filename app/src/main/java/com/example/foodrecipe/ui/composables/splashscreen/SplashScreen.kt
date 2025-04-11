package com.example.foodrecipe.ui.composables.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.foodrecipe.R
import com.example.foodrecipe.ui.composables.splashscreen.viewmodel.SplashScreenViewModel
import com.example.foodrecipe.ui.theme.RecipeAppColor
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    splashScreenViewModel: SplashScreenViewModel,
    oncompletion: () -> Unit
) {
    LaunchedEffect(Unit) {
        splashScreenViewModel.initialize()
        splashScreenViewModel.uiState.collectLatest {
            oncompletion()
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = RecipeAppColor.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            contentDescription = null,
            painter = painterResource(R.drawable.splash_logo),
            modifier = Modifier
                .width(200.dp)
        )

    }
}