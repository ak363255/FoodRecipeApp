package com.example.foodrecipe.ui.composables.recipeHomepage

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.data.Convertor
import com.example.domain.model.CusineName
import com.example.domain.model.IngredientName
import com.example.domain.model.Recipe
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.BottomSheetRoute
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.RecipeMainScreenRoute
import com.example.foodrecipe.ui.composables.recipeHomepage.ingredientbottomsheet.viewmodel.IngredientBottomSheetViewModel
import com.example.foodrecipe.ui.composables.recipeHomepage.viewmodel.RecipeHomePageEffects
import com.example.foodrecipe.ui.composables.recipeHomepage.viewmodel.RecipeHomePageViewEvent
import com.example.foodrecipe.ui.composables.recipeHomepage.viewmodel.RecipeHomePageViewModel
import com.example.foodrecipe.ui.theme.RecipeAppColor
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URLEncoder


@Composable
fun ShowToast(msg: String) {
    val context = LocalContext.current
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun RecipeHomePageScreen(
    modifier: Modifier = Modifier,
    recipeHomePageViewModel: RecipeHomePageViewModel,
    mainNavController: NavHostController
) {
    val uiState by recipeHomePageViewModel.states.collectAsState()
    val homePageController = rememberNavController()
    LaunchedEffect(homePageController) {
        homePageController.addOnDestinationChangedListener { _, destination, _ ->
            val ingredientroute =
                RecipeHomePageRoute.IngredientBottomSheet::class.java.canonicalName
            Log.d(
                "PATH",
                "path intercepted currentpath - ${destination.route} ing ${ingredientroute}"
            )
            if (destination.route == ingredientroute) {
                Log.d(
                    "PATH",
                    "path intercepted currentpath - ${destination.route} ing ${ingredientroute}"
                )
            } else {
                Log.d("PATH", "path noot matched")

            }
        }
    }

    Loader(modifier = modifier, isLoading = uiState.isLoading) {
        Surface(modifier = Modifier.fillMaxSize()) {
            LaunchedEffect(Unit) {
                recipeHomePageViewModel.effects.collectLatest {
                    when (it) {
                        is RecipeHomePageEffects.OpenCuisineSearchPage -> {}
                        RecipeHomePageEffects.OpenIngredientSearchPage -> {}
                        is RecipeHomePageEffects.OpenIngredientSheet -> {
                            val ingredients = it.ingredientNames
                            //open ingredient bottom sheet
                            val jsonData = Convertor.convertDataToJson(ingredients)
                            val encodedData =
                                URLEncoder.encode(Json.encodeToString(ingredients), "UTF-8")
                            Log.d("CUSTOM_DATA", "transfer ${encodedData}")
                            mainNavController.navigate("${BottomSheetRoute.IngredientBottomSheet.route}/${encodedData}")
                        }

                        is RecipeHomePageEffects.OpenRecipeDetailPage -> {
                            val data = Convertor.convertDataToJson(it.recipe)
                            mainNavController.navigate(RecipeMainScreenRoute.RecipeDetailPage(data = data))
                        }
                    }
                }
            }
            if (!uiState.hasError) {
                NavHost(
                    navController = homePageController,
                    startDestination = RecipeHomePageRoute.RecipeHomePage
                ) {
                    composable<RecipeHomePageRoute.RecipeHomePage> {
                        HomePageComponents(
                            modifier = Modifier,
                            recipeList = uiState.randomRecipes,
                            cuisines = uiState.cuisines,
                            ingredients = uiState.ingredients,
                            viewAllIngredients = { ingredients ->
                                recipeHomePageViewModel.processEvent(
                                    RecipeHomePageViewEvent.ViewAllIngredientClick(
                                        ingredients
                                    )
                                )
                            },
                            recipeItemClicked = {item ->
                                recipeHomePageViewModel.processEvent(RecipeHomePageViewEvent.RecipeItemClick(item))

                            }
                        )
                    }

                    composable<RecipeHomePageRoute.IngredientBottomSheet> {

                    }
                }

            }
            if (uiState.hasError) {
                ErrorPage()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientsBottomSheet(
    modifier: Modifier = Modifier,
    viewmodel: IngredientBottomSheetViewModel
) {

    val uiState by viewmodel.states.collectAsState()

    Loader(modifier = modifier, isLoading = uiState.isLoading) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = RecipeAppColor.Black)
        ) {
            if (!uiState.hasError) {
                Text("Success", color = RecipeAppColor.LightOrange)
            }
            if (uiState.hasError) {
                Text("Error", color = RecipeAppColor.LightOrange)
            }
        }
    }
}

sealed interface RecipeHomePageRoute {
    @Serializable
    data class IngredientBottomSheet(val ingredientNames: String) : RecipeHomePageRoute

    @Serializable
    data object RecipeHomePage : RecipeHomePageRoute

    @Serializable
    data class RecipeDetailPage(val recipe: Recipe) : RecipeHomePageRoute

}

fun RecipeHomePageRoute.route() = this.javaClass.canonicalName


@Composable
fun HomePageComponents(
    modifier: Modifier = Modifier,
    recipeList: List<Recipe>,
    ingredients: List<IngredientName>,
    cuisines: List<CusineName>,
    viewAllIngredients: (ingredients: List<IngredientName>) -> Unit,
    recipeItemClicked:(Recipe)-> Unit
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            DailyInspirationScreen(recipeList = recipeList,itemClicked = {item ->
                 recipeItemClicked(item)
            })
            Spacer(modifier = Modifier.height(32.dp))
        }
        item {
            QuickSearchByIngredientScreen(
                ingredients = ingredients,
                viewallIngredientClicked = viewAllIngredients
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            CuisineScreen(
                modifier = Modifier, cuisines = cuisines
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }

}

@Composable
fun CuisineScreen(modifier: Modifier, cuisines: List<CusineName>) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(RecipeAppColor.bottomBarBgColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "What Cuisines Are You Looking for ?")
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
        ) {
            items(count = cuisines.size) { index ->
                CuisineItem(cuisine = cuisines[index])
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun QuickSearchByIngredientScreen(
    modifier: Modifier = Modifier, ingredients: List<IngredientName>,
    viewallIngredientClicked: (List<IngredientName>) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(RecipeAppColor.bottomBarBgColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Quick Search by Ingredients")
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp)
        ) {
            items(count = Math.min(8, ingredients.size)) { index ->
                IngredientItem(modifier = Modifier, ingredientName = ingredients[index])
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            textAlign = TextAlign.Center,
            text = "View all Ingredients",
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .background(shape = RoundedCornerShape(8.dp), color = RecipeAppColor.White)
                .padding(horizontal = 48.dp, vertical = 12.dp)
                .clickable {
                    viewallIngredientClicked(ingredients)
                },
            style = TextStyle(
                color = RecipeAppColor.bottomBarBgColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.heightIn(16.dp))


    }
}

@Composable
fun IngredientItem(modifier: Modifier = Modifier, ingredientName: IngredientName) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(color = ingredientName.color.toColor())
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = ingredientName.name, style = TextStyle(
                fontSize = 14.sp,
                color = RecipeAppColor.White,
                fontWeight = FontWeight.Normal,

                )
        )
    }
}

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}

@Composable
fun DailyInspirationScreen(modifier: Modifier = Modifier, recipeList: List<Recipe>,itemClicked:(Recipe)-> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Daily Inspiration",
            style = TextStyle(
                fontSize = 24.sp, fontWeight = FontWeight.Bold, color = RecipeAppColor.White
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            contentPadding = PaddingValues(start = 16.dp), modifier = Modifier

        ) {
            items(recipeList.size) { index ->
                DailyInspirationItem(recipe = recipeList[index], itemClicked = itemClicked)
                Spacer(modifier = Modifier.width(12.dp))
            }
        }

    }
}

@Composable
fun CuisineItem(modifier: Modifier = Modifier, cuisine: CusineName) {
    val itemWidth = 140.dp
    Box(
        modifier = modifier.width(itemWidth)
    ) {
        Box(
            modifier = Modifier
                .width(itemWidth)
                .aspectRatio(1f)
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = cuisine.color.toColor()),
        )
        Text(
            text = cuisine.name,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )

    }
}


@Composable
fun DailyInspirationItem(modifier: Modifier = Modifier, recipe: Recipe,itemClicked: (Recipe) -> Unit) {
    val itemWidth = 200.dp
    Box(
        modifier = modifier.width(itemWidth)
            .clickable{
                itemClicked(recipe)
            }
    ) {

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            AsyncImage(
                model = recipe.image,
                contentDescription = null,
                modifier = Modifier
                    .width(itemWidth)
                    .aspectRatio(12 / 9f)
                    .clip(shape = RoundedCornerShape(12.dp)),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "${recipe.readyInMinutes} MIN", style = TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RecipeAppColor.White
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = recipe.title, style = TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RecipeAppColor.White
                ), maxLines = 1, overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "by ${recipe.sourceName}", style = TextStyle(
                    fontSize = 12.sp, fontWeight = FontWeight.Normal, color = RecipeAppColor.White
                ), maxLines = 1, overflow = TextOverflow.Ellipsis
            )

        }
        Icon(
            imageVector = Icons.Default.Bookmark,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.TopEnd)
                .padding(end = 8.dp, top = 8.dp),
            tint = RecipeAppColor.White
        )

    }
}


@Composable
fun ErrorPage(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            "Something went wrong!!", style = TextStyle(
                fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White
            )
        )
    }
}

@Composable
fun Loader(modifier: Modifier = Modifier, isLoading: Boolean, content: @Composable () -> Unit) {

    Crossfade(targetState = isLoading, modifier = modifier) { it ->
        when (it) {
            true -> CircularLoadingIndicator()
            false -> content()
        }
    }
}

@Composable
fun CircularLoadingIndicator() = Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier.fillMaxSize()
) {
    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(40.dp)
    )
}