package com.example.foodrecipe.ui.composables.recipedetailpage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import com.example.domain.model.Recipe
import com.example.domain.model.Step
import com.example.foodrecipe.ui.composables.recipeHomepage.Loader
import com.example.foodrecipe.ui.composables.recipedetailpage.viewmodel.RecipeDetailPageViewModel
import com.example.foodrecipe.ui.theme.RecipeAppColor


@Composable
fun RecipeDetailPageScreen(modifier: Modifier = Modifier, viewmodel: RecipeDetailPageViewModel) {
    val viewstate by viewmodel.states.collectAsState()
    Loader(modifier = modifier, isLoading = viewstate.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            if (!viewstate.isError && viewstate.recipe != null) {
                RecipeDetailPageUi(modifier = Modifier, recipe = viewstate.recipe!!)
            }
            if (viewstate.isError) {
                Text("Error")
            }


        }
    }
}

@Composable
fun RecipeDetailPageUi(modifier: Modifier = Modifier, recipe: Recipe) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
            .background(color = RecipeAppColor.Black)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (image, detailview) = createRefs()
            AsyncImage(
                model = recipe.image,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.40f)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            ConstraintLayout(
                modifier = Modifier
                    .zIndex(2f)
                    .constrainAs(detailview) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .graphicsLayer {
                        translationY = -34.dp.toPx()
                    }
                    .background(
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                        color = RecipeAppColor.Black
                    )


            ) {
                val (whiteTopbar, title, source, actionview) = createRefs()

                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(4.dp)
                        .background(
                            shape = RoundedCornerShape(percent = 50),
                            color = RecipeAppColor.White
                        )
                        .constrainAs(whiteTopbar) {
                            top.linkTo(parent.top, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(title) {
                            top.linkTo(whiteTopbar.bottom, margin = 32.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        },
                    text = recipe.title,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = RecipeAppColor.White
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(source) {
                            top.linkTo(title.bottom, margin = 4.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        },
                    text = recipe.sourceName,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = RecipeAppColor.White.copy(alpha = 0.8f)
                    ),
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(actionview) {
                            top.linkTo(source.bottom, margin = 32.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)

                        }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ActionUi(
                            icon = Icons.Default.Favorite,
                            actionName = recipe.aggregateLikes.toString(),
                            tint = RecipeAppColor.Green
                        )
                        ActionUi(
                            icon = Icons.Default.Stars,
                            actionName = recipe.healthScore.toString(),
                            tint = RecipeAppColor.White
                        )
                        ActionUi(
                            icon = Icons.Default.Bookmark,
                            actionName = "Save",
                            tint = RecipeAppColor.White

                        )
                        ActionUi(
                            icon = Icons.Default.IosShare,
                            actionName = "Share",
                            tint = RecipeAppColor.White
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 4.dp, end = 4.dp, bottom = 16.dp)
                            .height(2.dp)
                            .background(color = RecipeAppColor.White)
                    )
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Row {
                            Text(
                                text = recipe.pricePerServing.toString(),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RecipeAppColor.White
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Calories",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RecipeAppColor.White.copy(alpha = 0.5f)
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(48.dp))
                        Row {
                            Text(
                                text = recipe.readyInMinutes.toString(),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RecipeAppColor.White
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Minutes",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RecipeAppColor.White.copy(alpha = 0.5f)
                                )
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 4.dp, end = 4.dp, bottom = 16.dp)
                            .height(2.dp)
                            .background(color = RecipeAppColor.White)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        ContentItem(
                            contentName = "Protein",
                            contentPercent = "72.78 %"
                        )
                        Spacer(modifier = Modifier.width(32.dp))
                        ContentItem(
                            contentName = "Carbs",
                            contentPercent = "${(100-72.78)/2} %"
                        )
                        Spacer(modifier = Modifier.width(32.dp))
                        ContentItem(
                            contentName = "Fat",
                            contentPercent = "${(100-72.78)/2} %"
                        )
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                    InstructionView(
                        modifier = Modifier,
                        instructions = recipe.analyzedInstructions.first().steps
                    )
                    Spacer(modifier = Modifier.height(56.dp))

                }


            }

        }
    }
}

@Composable
fun InstructionView(modifier: Modifier = Modifier, instructions: List<Step?>) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var selectedIndex by remember {
            mutableIntStateOf(0)
        }
        Text(
            text = "Method",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = RecipeAppColor.White
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = instructions[selectedIndex]?.step ?: "",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = TextStyle(fontSize = 12.sp, color = RecipeAppColor.White)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier
                .wrapContentSize()
        ) {
            item {
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    instructions.forEachIndexed { index, item ->
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(color = RecipeAppColor.Black)
                                .clickable {
                                    selectedIndex = index
                                }
                        ) {
                            Text(
                                text = (index + 1).toString(),
                                modifier = Modifier
                                    .background(
                                        shape = if (index == 0) RoundedCornerShape(
                                            topStart = 16.dp,
                                            bottomStart = 16.dp
                                        ) else if (index == instructions.size - 1) RoundedCornerShape(
                                            topEnd = 16.dp,
                                            bottomEnd = 16.dp
                                        ) else RectangleShape,
                                        color = if (selectedIndex == index) RecipeAppColor.White else RecipeAppColor.bottomBarBgColor
                                    )
                                    .padding(horizontal = 16.dp, vertical = 6.dp),
                                style = TextStyle(color = if (selectedIndex == index) RecipeAppColor.Black else RecipeAppColor.White)
                            )

                        }
                        Spacer(
                            modifier = Modifier
                                .background(color = RecipeAppColor.Black)
                                .width(2.dp)
                                .fillMaxHeight()
                        )
                    }

                }
            }

        }

    }
}

@Composable
fun ContentItem(modifier: Modifier = Modifier, contentName: String, contentPercent: String) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(color = RecipeAppColor.White)
                .padding(1.dp)
                .clip(CircleShape)
                .background(color = RecipeAppColor.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = contentPercent,
                style = TextStyle(fontSize = 14.sp, color = RecipeAppColor.White)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = contentName, style = TextStyle(fontSize = 14.sp, color = RecipeAppColor.White))

    }
}

@Composable
fun ActionUi(modifier: Modifier = Modifier, icon: ImageVector, actionName: String, tint: Color) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(22.dp),
            tint = tint
        )
        Text(
            text = actionName,
            modifier = Modifier,
            style = TextStyle(
                fontSize = 12.sp,
                color = RecipeAppColor.White,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}