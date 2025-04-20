import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.FloatingWindow
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.NavigatorState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.BottomSheetRoute
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.rememberSheetState
import com.example.foodrecipe.ui.composables.recipeHomepage.bottomsheetwrapper.BottomSheetNavHost
import com.example.foodrecipe.ui.composables.recipeHomepage.bottomsheetwrapper.BottomSheetNavigator
import com.example.foodrecipe.ui.composables.recipeHomepage.bottomsheetwrapper.bottomSheet


@OptIn(ExperimentalMaterial3Api::class)
@Navigator.Name("Material3BottomSheetNavigator")
class Material3BottomSheetNavigator(
    val sheetState: SheetState
) : Navigator<Material3BottomSheetNavigator.Destination>() {

    private var attached by mutableStateOf(false)
    private lateinit var navState: NavigatorState

    override fun onAttach(state: NavigatorState) {
        super.onAttach(state)
        navState = state
        attached = true
    }

    override fun createDestination(): Destination = Destination(this)

    override fun navigate(
        entries: List<NavBackStackEntry>,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ) {

        // Remove any existing bottom sheet entry
        val current = navState.backStack.value.lastOrNull()
        if (current?.destination is Destination) {
            navState.pop(current, false)
        }
        entries.forEach { entry ->
            navState.push(entry)
        }
    }

    override fun popBackStack(popUpTo: NavBackStackEntry, savedState: Boolean) {
        navState.pop(popUpTo, savedState)
    }

    @Composable
    fun BottomSheetHost() {
        val backStackEntry = navState.backStack.collectAsState()
        val latestEntry = backStackEntry.value.lastOrNull { entry ->
            // We might have entries in the back stack that aren't started currently, so filter
            // these
            Log.d("CUSTOM", "entry ${entry.destination.route}")
            entry.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        }
        if (latestEntry != null) {
            val destination = latestEntry.destination as? Destination
            ModalBottomSheet(
                onDismissRequest = {
                    navState.pop(latestEntry, false)
                },
                sheetState = sheetState
            ) {
                BackHandler(enabled = true) {
                    navState.pop(latestEntry, false)
                }
                destination?.content?.invoke(latestEntry)
            }
        }
    }

    @NavDestination.ClassType(Composable::class)
    class Destination(
        navigator: Material3BottomSheetNavigator
    ) : NavDestination(navigator), FloatingWindow {
        lateinit var content: @Composable (NavBackStackEntry) -> Unit
    }
}

fun NavGraphBuilder.dialogNavigatorDestination(
    route: String,
    navigator: Material3DialogNavigator,
    content: @Composable (NavBackStackEntry) -> Unit,
    titleContent: @Composable () -> Unit
) {
    val destination = navigator.createDestination().apply {
        this.route = route
        this.content = content
        this.titleContent = titleContent

    }
    addDestination(destination)
}

fun NavGraphBuilder.bottomSheetNavigatorDestination(
    route: String,
    navigator: Material3BottomSheetNavigator,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    val destination = navigator.createDestination().apply {
        this.route = route
        this.content = content
    }
    addDestination(destination)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val sheetState = rememberSheetState()
    val navigator = remember(sheetState) { BottomSheetNavigator(sheetState) }
    val navController = rememberNavController()
    BottomSheetNavHost(
        bottomSheetNavigator = navigator
    ) {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text("Home", modifier = Modifier.clickable {
                        navController.navigate("sheet")
                    })
                }
            }
            bottomSheet(route = BottomSheetRoute.IngredientBottomSheet.route, navigator = navigator) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "Hi From bottom sheet content")
                }
            }

        }
    }
    // Bottom Sheet Navigation Host
    /* BottomSheetNavHost(
         navController = navController,
         navigator = navigator,
         dialogNavigator = dailogNavigator,
         startDestination = "home"
     )*/
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(navController: NavController) {
    ModalBottomSheet(
        onDismissRequest = {
            navController.popBackStack()  // Handle back navigation
        },
        sheetState = rememberSheetState(),
        content = {
            // Your content here
            Column(modifier = Modifier.padding(16.dp)) {
                Text("This is the bottom sheet content.")
                Button(
                    onClick = { navController.popBackStack() }  // Close the bottom sheet
                ) {
                    Text("Close")
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Navigator.Name("Material3DialogNavigator")
class Material3DialogNavigator : Navigator<Material3DialogNavigator.Destination>() {

    private var attached by mutableStateOf(false)
    private lateinit var navState: NavigatorState

    override fun onAttach(state: NavigatorState) {
        super.onAttach(state)
        navState = state
        attached = true
    }

    override fun createDestination(): Destination = Destination(this)

    override fun navigate(
        entries: List<NavBackStackEntry>,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ) {
        entries.forEach { entry ->
            navState.push(entry)
        }
    }

    override fun popBackStack(popUpTo: NavBackStackEntry, savedState: Boolean) {
        navState.pop(popUpTo, savedState)
    }

    @Composable
    fun DialogHost() {
        val backStackEntry = navState.backStack.collectAsState()
        val latestEntry = backStackEntry.value.lastOrNull {
            it.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        }

        if (latestEntry != null) {
            val destination = latestEntry.destination as? Destination
            AlertDialog(
                onDismissRequest = {
                    navState.pop(latestEntry, false)
                },
                title = {
                    destination?.titleContent() ?: Box {}
                },
                text = {
                    destination?.content?.invoke(latestEntry)
                },
                confirmButton = {
                    TextButton(onClick = {
                        navState.pop(latestEntry, false)
                    }) {
                        Text("OK")
                    }
                }
            )
        }
    }

    @NavDestination.ClassType(Composable::class)
    class Destination(navigator: Material3DialogNavigator) :
        NavDestination(navigator), FloatingWindow {
        lateinit var content: @Composable (NavBackStackEntry) -> Unit
        lateinit var titleContent: @Composable () -> Unit
    }
}






