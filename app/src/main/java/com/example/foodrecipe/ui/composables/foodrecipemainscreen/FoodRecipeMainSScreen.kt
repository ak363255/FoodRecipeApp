package com.example.foodrecipe.ui.composables.foodrecipemainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodrecipe.ui.composables.apponboarding.AppBoardingScreen
import com.example.foodrecipe.ui.composables.apponboarding.pageList
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.FoodRecipeMainViewModel
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewEffect
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel


@Composable
fun FoodRecipeMainScreen(
    foodRecipeMainViewModel: FoodRecipeMainViewModel,
    modifier: Modifier = Modifier,
    onFinished: () -> Unit,
) {
    val navController = rememberNavController()
    LaunchedEffect(Unit) {
        foodRecipeMainViewModel.processEvent(ViewEvent.AppOnboardingStatus)
        launch {
            foodRecipeMainViewModel.effects.collect { effect ->
                launch {
                    when (effect) {
                        ViewEffect.OpenRecipeHomePage -> {
                            navController.navigate(RecipeHomePageRoute.RecipeHomePage)
                        }
                    }
                }
            }
        }
        delay(1000)
        onFinished()
    }
    val uiState by foodRecipeMainViewModel.states.collectAsState()
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val initialDestination =
                if (uiState.showAppOnBoarding) RecipeHomePageRoute.AppOnboardingPage else RecipeHomePageRoute.RecipeHomePage
            NavHost(
                navController = navController,
                startDestination = initialDestination,
                route = Graph.Root::class
            ) {
                composable<RecipeHomePageRoute.AppOnboardingPage> {
                    AppBoardingScreen(
                        onboardingCompletedAction = {
                            foodRecipeMainViewModel.processEvent(ViewEvent.AppOnboardingCompletedEvent)
                        },
                        modifier = Modifier,
                        appOnboardingViewModel = koinViewModel(),
                        onBoardingPageList = pageList
                    )
                }
                composable<RecipeHomePageRoute.RecipeHomePage> {

                }
            }
        }
    }
}


sealed class RecipeHomePageRoute {
    @Serializable
    data object AppOnboardingPage : RecipeHomePageRoute()

    @Serializable
    data object RecipeHomePage : RecipeHomePageRoute()
}

sealed class Graph {
    @Serializable
    data object Root : Graph()
}

fun RecipeHomePageRoute.route() = this.javaClass.canonicalName


