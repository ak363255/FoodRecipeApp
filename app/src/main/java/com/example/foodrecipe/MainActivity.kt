package com.example.foodrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.FoodRecipeMainScreen
import com.example.foodrecipe.ui.composables.splashscreen.SplashScreen
import com.example.foodrecipe.ui.theme.FoodRecipeTheme
import com.example.foodrecipe.ui.theme.RecipeAppColor
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            var splashCompleted by remember {
                mutableStateOf(false)
            }
            FoodRecipeTheme {
                Box {
                    FoodRecipeMainScreen(foodRecipeMainViewModel = koinViewModel())
                    AnimatedVisibility(visible = !splashCompleted, exit = ExitTransition.None) {
                        SplashScreen(
                            splashScreenViewModel = koinViewModel()
                        ) {
                            splashCompleted = true
                        }
                    }
                }
            }
        }
    }
}