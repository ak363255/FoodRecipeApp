package com.example.foodrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.FoodRecipeMainScreen
import com.example.foodrecipe.ui.theme.FoodRecipeTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    private var showSplashcreen = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen().setKeepOnScreenCondition {
            showSplashcreen
        }
        setContent {
            FoodRecipeTheme {
                FoodRecipeMainScreen(
                    foodRecipeMainViewModel = koinViewModel()
                ) {
                    showSplashcreen = false
                }
            }
        }
    }
}