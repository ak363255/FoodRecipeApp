package com.example.foodrecipe.ui.composables.recipeHomepage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.foodrecipe.ui.composables.recipeHomepage.viewmodel.RecipeHomePageViewEvent
import com.example.foodrecipe.ui.composables.recipeHomepage.viewmodel.RecipeHomePageViewModel

@Composable
fun RecipeHomePageScreen(
    modifier : Modifier = Modifier,
    recipeHomePageViewModel: RecipeHomePageViewModel
){
    LaunchedEffect(Unit) {
        recipeHomePageViewModel.processEvent(RecipeHomePageViewEvent.GetAllRecipeHomePageContent)
    }
    val uiState by recipeHomePageViewModel.states.collectAsState()

    Box(modifier = modifier.fillMaxSize()){
        if(uiState.isLoading){
           Text("Loading",color = Color.Red)
        }else if(uiState.hasError){
            Text("Error",color = Color.Red)
        }else{
           Text("Success",color = Color.Red)
        }
    }


}