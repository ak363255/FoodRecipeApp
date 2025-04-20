package com.example.foodrecipe.ui.composables.recipeHomepage.bottomsheetwrapper

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.navigation.FloatingWindow
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.NavigatorState

@OptIn(ExperimentalMaterial3Api::class)
@Navigator.Name("BottomSheetNavigator")
class BottomSheetNavigator(
    val sheetState: SheetState
) : Navigator<BottomSheetNavigator.Destination>() {

    private var attached by mutableStateOf(false)
    private var navState: NavigatorState? = null

    override fun onAttach(state: NavigatorState) {
        super.onAttach(state)
        navState = state
        attached = true
    }

    override fun createDestination(): Destination{
        return Destination(this)
    }

    override fun navigate(
        entries: List<NavBackStackEntry>,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ) {

        // Remove any existing bottom sheet entry
        val current = navState?.backStack?.value?.lastOrNull()
        if (current?.destination is Destination) {
            navState?.pop(current, false)
        }
        entries.forEach { entry ->
            navState?.push(entry)
        }
    }

    override fun popBackStack(popUpTo: NavBackStackEntry, savedState: Boolean) {
        navState?.pop(popUpTo, savedState)
    }

    @Composable
    fun BottomSheetHost() {
        val backStackEntry = navState?.backStack?.collectAsState()
        val latestEntry = backStackEntry?.value?.lastOrNull { entry ->
            Log.d("CUSTOM", "entry ${entry.destination.route}")
            entry.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        }

        if (latestEntry != null) {
            val destination = latestEntry.destination as? Destination
            ModalBottomSheet(
                onDismissRequest = {
                    navState?.pop(latestEntry, false)
                },
                sheetState = sheetState,
                dragHandle = {}
            ) {
                BackHandler(enabled = true) {
                    navState?.pop(latestEntry, false)
                }
                destination?.content?.invoke(latestEntry)
            }
        }
    }

    @NavDestination.ClassType(Composable::class)
    class Destination(
        navigator: BottomSheetNavigator,
    ) : NavDestination(navigator), FloatingWindow {
        lateinit var content: @Composable (NavBackStackEntry) -> Unit
        var navArguments: List<NamedNavArgument> = emptyList()

    }
}