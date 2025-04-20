package com.example.foodrecipe.ui.composables.foodrecipemainscreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.data.Convertor
import com.example.domain.model.IngredientName
import com.example.foodrecipe.ui.composables.apponboarding.AppBoardingScreen
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.FoodRecipeMainViewModel
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewEffect
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewEvent
import com.example.foodrecipe.ui.composables.recipeHomepage.IngredientItem
import com.example.foodrecipe.ui.composables.recipeHomepage.RecipeHomePageScreen
import com.example.foodrecipe.ui.composables.recipeHomepage.bottomsheetwrapper.BottomSheetNavHost
import com.example.foodrecipe.ui.composables.recipeHomepage.bottomsheetwrapper.BottomSheetNavigator
import com.example.foodrecipe.ui.composables.recipeHomepage.bottomsheetwrapper.bottomSheet
import com.example.foodrecipe.ui.theme.RecipeAppColor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import org.koin.androidx.compose.koinViewModel
import java.net.URLDecoder


sealed class BottomBarNavModel(
    val icon: ImageVector,
    val title: String,
    val route: RecipeMainScreenRoute
) {
    data object Discover : BottomBarNavModel(
        icon = Icons.Default.Menu,
        title = "Discover",
        route = RecipeMainScreenRoute.RecipeHomePage
    )

    data object BookMark : BottomBarNavModel(
        icon = Icons.Default.Bookmark,
        title = "BookMark",
        route = RecipeMainScreenRoute.BookmarkPage
    )

    data object MealPlan : BottomBarNavModel(
        icon = Icons.Default.SetMeal,
        title = "Meal Plan",
        route = RecipeMainScreenRoute.MealPlanPage
    )

    data object Setting : BottomBarNavModel(
        icon = Icons.Default.Settings,
        title = "Setting",
        route = RecipeMainScreenRoute.SettingPage
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
    onNavItemClicked: (BottomBarNavModel) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
        modifier = modifier
            .padding(bottom = 24.dp)
            .fillMaxWidth()
            .background(color = RecipeAppColor.bottomBarBgColor),

        ) {
        bottomBarList.forEachIndexed { index, item ->
            Log.d(
                "Route ",
                " route - ${navController.currentBackStackEntryAsState().value?.destination?.route}"
            )
            val isSelected =
                navController.currentBackStackEntryAsState().value?.destination?.route == item.route.route()
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

                    ) {
                        onNavItemClicked(item)
                    }
                    .weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (isSelected) RecipeAppColor.Green else Color.White
                    )
                    Text(
                        text = item.title,
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSelected) RecipeAppColor.Green else Color.White
                        )
                    )
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberSheetState(): SheetState {
    return rememberModalBottomSheetState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodRecipeMainScreen(
    foodRecipeMainViewModel: FoodRecipeMainViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by foodRecipeMainViewModel.states.collectAsState()
    val bottomNavState = rememberSaveable { mutableStateOf(true) }

    val bottomSheetState = rememberSheetState()
    val bottomSheetNavigator = remember(bottomSheetState) { BottomSheetNavigator(bottomSheetState) }
    val navController = rememberNavController(bottomSheetNavigator)
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black),
        bottomBar = {
            AnimatedVisibility(visible = bottomNavState.value) {
                RecipeBottomBar(
                    navController = navController,
                    bottomBarList = bottomNavBarList
                ) { item ->
                    val route = navController.currentBackStackEntry?.destination?.route
                    if (route != item.route.route()) {
                        navController.navigate(item.route) {
                            popUpTo<RecipeMainScreenRoute.RecipeHomePage> {
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
            val initialDestination =
                if (uiState.showAppOnBoarding) RecipeMainScreenRoute.AppOnboardingPage else RecipeMainScreenRoute.RecipeHomePage
            BottomSheetNavHost(
                modifier = Modifier,
                bottomSheetNavigator = bottomSheetNavigator
            ) {
                LaunchedEffect(Unit) {
                    navController.addOnDestinationChangedListener { controller, destination, argument ->
                        //handle bottom nav state
                    }
                    foodRecipeMainViewModel.processEvent(ViewEvent.AppOnboardingStatus)
                    foodRecipeMainViewModel.effects.collectLatest { effect ->
                        when (effect) {
                            ViewEffect.OpenRecipeHomePage -> {
                                // bottoMNavState.value = true
                                navController.navigate(RecipeMainScreenRoute.RecipeHomePage) {
                                    popUpTo<RecipeMainScreenRoute.AppOnboardingPage> {
                                        inclusive = true
                                    }
                                }
                            }
                        }

                    }
                }
                NavHost(
                    navController = navController,
                    startDestination = initialDestination,
                    route = Graph.Root::class
                ) {
                    composable<RecipeMainScreenRoute.AppOnboardingPage> {
                        bottomNavState.value = false
                        AppBoardingScreen(
                            onActionCompleted = {
                                foodRecipeMainViewModel.processEvent(ViewEvent.AppOnboardingCompletedEvent)
                            },
                            modifier = Modifier,
                            appOnboardingViewModel = koinViewModel(),
                        )
                    }
                    composable<RecipeMainScreenRoute.RecipeHomePage> {
                        bottomNavState.value = true
                        RecipeHomePageScreen(
                            modifier = Modifier,
                            recipeHomePageViewModel = koinViewModel(),
                            mainNavController = navController
                        )
                    }
                    composable<RecipeMainScreenRoute.BookmarkPage> {
                        Text("BookMark")

                    }
                    composable<RecipeMainScreenRoute.MealPlanPage> {
                        Text("Meal Plan")

                    }
                    composable<RecipeMainScreenRoute.SettingPage> {
                        Text("Setting")

                    }
                    bottomSheet(
                        route = "ingredient_sheet/{routeArg}",
                        navigator = bottomSheetNavigator,
                        arguments = listOf(
                            navArgument("routeArg") {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->
                        val jsonData = backStackEntry.arguments?.getString("routeArg")
                        Log.d("CUSTOM_DATA", "size ${jsonData}")
                        jsonData?.let { jsonData ->
                            val decodedData = URLDecoder.decode(jsonData, "UTF-8")
                            val ingredients: List<IngredientName>? =
                                Convertor.convertJsonToData<List<IngredientName>>(decodedData)
                            Log.d("CUSTOM_DATA", "ingred ${ingredients}")
                            ingredients?.let {
                                IngredientBottomSheet(
                                    modifier = Modifier,
                                    ingredients = it
                                )
                            }

                        }


                    }
                }
            }

        }


    }


}

@Composable
fun IngredientBottomSheet(modifier: Modifier = Modifier, ingredients: List<IngredientName>) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = RecipeAppColor.Black.copy(alpha = 0.95f))
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            state = rememberLazyListState(),

            ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        text = "Ingredients",
                        style = TextStyle(
                            color = RecipeAppColor.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.heightIn(32.dp))
            }


            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 1000.dp)
                ) {
                    items(count = ingredients.size) { index ->
                        IngredientItem(modifier = Modifier, ingredientName = ingredients[index])
                    }
                }
            }


        }
    }
}


sealed class RecipeMainScreenRoute {
    @Serializable
    data object AppOnboardingPage : RecipeMainScreenRoute()

    @Serializable
    data object RecipeHomePage : RecipeMainScreenRoute()

    @Serializable
    data object BookmarkPage : RecipeMainScreenRoute()


    @Serializable
    data object MealPlanPage : RecipeMainScreenRoute()


    @Serializable
    data object SettingPage : RecipeMainScreenRoute()


}

@Serializable
sealed class BottomSheetRoute(val route: String) {
    @Serializable
    data object IngredientBottomSheet : BottomSheetRoute("ingredient_sheet")
}

sealed class Graph {
    @Serializable
    data object Root : Graph()
}

fun RecipeMainScreenRoute.route() = this.javaClass.canonicalName


