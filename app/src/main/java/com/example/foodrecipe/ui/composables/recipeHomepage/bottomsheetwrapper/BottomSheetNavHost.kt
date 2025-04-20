package com.example.foodrecipe.ui.composables.recipeHomepage.bottomsheetwrapper

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import kotlin.reflect.KType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetNavHost(
    modifier: Modifier = Modifier,
    bottomSheetNavigator: BottomSheetNavigator,
    content:@Composable ()-> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        content()
        bottomSheetNavigator.BottomSheetHost()
    }
}


/*public  fun NavGraphBuilder.bottomSheet(
    route: String,
    navigator: BottomSheetNavigator,
    arguments: List<NamedNavArgument> = emptyList(),
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    EnterTransition?)? =
        null,
    exitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    ExitTransition?)? =
        null,
    popEnterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    EnterTransition?)? =
        enterTransition,
    popExitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    ExitTransition?)? =
        exitTransition,
    sizeTransform:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    SizeTransform?)? =
        null,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    val destination = navigator.createDestination().apply {
        this.content = content
        this.route = route
        arguments.forEach { (name, arg) ->
            addArgument(name, arg)
        }
        this.navArguments = arguments
    }
    addDestination(
        destination
    )
}*/

public   fun NavGraphBuilder.bottomSheet(
    route: String,
    navigator: BottomSheetNavigator,
    arguments: List<NamedNavArgument> = emptyList(),
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
     enterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    EnterTransition?)? =
        null,
     exitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    ExitTransition?)? =
        null,
     popEnterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    EnterTransition?)? =
        enterTransition,
     popExitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    ExitTransition?)? =
        exitTransition,
     sizeTransform:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    SizeTransform?)? =
        null,
     content: @Composable (NavBackStackEntry) -> Unit
) {
    val destination = navigator.createDestination().apply {
        this.content = content
        this.route = route
        arguments.forEach { (name, arg) ->
            addArgument(name, arg)
        }
        this.navArguments = arguments
    }
    addDestination(
        destination
    )
}