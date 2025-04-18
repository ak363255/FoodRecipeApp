package com.example.foodrecipe.ui.composables.foodrecipemainscreen
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodrecipe.ui.composables.apponboarding.AppBoardingScreen
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.FoodRecipeMainViewModel
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewEffect
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewEvent
import com.example.foodrecipe.ui.composables.recipeHomepage.RecipeHomePageScreen
import com.example.foodrecipe.ui.composables.recipeHomepage.viewmodel.RecipeHomePageViewModel
import com.example.foodrecipe.ui.theme.RecipeAppColor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel


sealed class BottomBarNavModel(
    val icon: ImageVector,
    val title: String,
    val route: RecipeHomePageRoute
) {
    data object Discover : BottomBarNavModel(
        icon = Icons.Default.Menu,
        title = "Discover",
        route = RecipeHomePageRoute.RecipeHomePage
    )

    data object BookMark : BottomBarNavModel(
        icon = Icons.Default.Bookmark,
        title = "BookMark",
        route = RecipeHomePageRoute.BookmarkPage
    )

    data object MealPlan : BottomBarNavModel(
        icon = Icons.Default.SetMeal,
        title = "Meal Plan",
        route = RecipeHomePageRoute.MealPlanPage
    )

    data object Setting : BottomBarNavModel(
        icon = Icons.Default.Settings,
        title = "Setting",
        route = RecipeHomePageRoute.SettingPage
    )
}

val bottomNavBarList = listOf(
    BottomBarNavModel.Discover,
    BottomBarNavModel.BookMark,
    BottomBarNavModel.MealPlan,
    BottomBarNavModel.Setting,
)

@Composable
fun RecipeBottomBar(
    modifier: Modifier = Modifier,
    bottomBarList: List<BottomBarNavModel>,
    navController: NavHostController,
    onNavItemClicked:(BottomBarNavModel)->Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
        modifier = modifier
            .padding(bottom = 24.dp)
            .fillMaxWidth()
            .background(color = RecipeAppColor.bottomBarBgColor),

    ) {
        bottomBarList.forEachIndexed { index, item ->
            Log.d("Route "," route - ${navController.currentBackStackEntryAsState().value?.destination?.route}")
            val isSelected = navController.currentBackStackEntryAsState().value?.destination?.route== item.route.route()
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 4.dp)
                    .clickable(
                        indication = ripple(
                            color = RecipeAppColor.Green,
                            radius = 64.dp,
                            bounded = false

                        ),
                        interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }

                    ){
                      onNavItemClicked(item)
                    }.weight(1f)
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if(isSelected) RecipeAppColor.Green else Color.White
                    )
                    Text(
                        text = item.title,
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if(isSelected) RecipeAppColor.Green else Color.White
                        )
                    )
                }
            }
        }

    }

}


@Composable
fun FoodRecipeMainScreen(
    foodRecipeMainViewModel: FoodRecipeMainViewModel,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val uiState by foodRecipeMainViewModel.states.collectAsState()
    val bottomNavState = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        foodRecipeMainViewModel.processEvent(ViewEvent.AppOnboardingStatus)
        foodRecipeMainViewModel.effects.collectLatest { effect ->
            when (effect) {
                ViewEffect.OpenRecipeHomePage -> {
                   // bottoMNavState.value = true
                    navController.navigate(RecipeHomePageRoute.RecipeHomePage) {
                        popUpTo<RecipeHomePageRoute.AppOnboardingPage> {
                            inclusive = true
                        }
                    }
                }
            }

        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black),
        bottomBar = {
            AnimatedVisibility(visible = bottomNavState.value) {
                RecipeBottomBar(
                    navController = navController,
                    bottomBarList = bottomNavBarList
                ){item ->
                    val route = navController.currentBackStackEntry?.destination?.route
                    if(route != item.route.route()){
                        navController.navigate(item.route){
                            popUpTo<RecipeHomePageRoute.RecipeHomePage>{
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val initialDestination = if (uiState.showAppOnBoarding) RecipeHomePageRoute.AppOnboardingPage  else RecipeHomePageRoute.RecipeHomePage
            NavHost(
                navController = navController,
                startDestination = initialDestination,
                route = Graph.Root::class
            ) {
                composable<RecipeHomePageRoute.AppOnboardingPage> {
                    bottomNavState.value = false
                    AppBoardingScreen(
                        onActionCompleted = {
                            foodRecipeMainViewModel.processEvent(ViewEvent.AppOnboardingCompletedEvent)
                        },
                        modifier = Modifier,
                        appOnboardingViewModel = koinViewModel(),
                    )
                }
                composable<RecipeHomePageRoute.RecipeHomePage> {
                    bottomNavState.value = true
                    RecipeHomePageScreen(
                        modifier = Modifier,
                        recipeHomePageViewModel = koinViewModel()
                    )
                }
                composable<RecipeHomePageRoute.BookmarkPage> {
                    Text("BookMark")

                }
                composable<RecipeHomePageRoute.MealPlanPage> {
                    Text("Meal Plan")

                }
                composable<RecipeHomePageRoute.SettingPage> {
                    Text("Setting")

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

    @Serializable
    data object BookmarkPage : RecipeHomePageRoute()


    @Serializable
    data object MealPlanPage : RecipeHomePageRoute()


    @Serializable
    data object SettingPage : RecipeHomePageRoute()
}

sealed class Graph {
    @Serializable
    data object Root : Graph()
}

fun RecipeHomePageRoute.route() = this.javaClass.canonicalName


