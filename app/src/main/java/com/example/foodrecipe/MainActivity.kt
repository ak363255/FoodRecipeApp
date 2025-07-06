package com.example.foodrecipe

import MainScreen
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.FoodRecipeMainScreen
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.SplashViewMode
import com.example.foodrecipe.ui.composables.splashscreen.SplashScreen
import com.example.foodrecipe.ui.theme.FoodRecipeTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val splashViewModel : SplashViewMode by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            splashViewModel.state.collect {
                Toast.makeText(this@MainActivity,"STATE value -> ${it}", Toast.LENGTH_LONG).show()
            }
        }
        splashViewModel.initialize()
        setContent {
            var splashCompleted by remember {
                mutableStateOf(false)
            }
            FoodRecipeTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                   // MainScreen()
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