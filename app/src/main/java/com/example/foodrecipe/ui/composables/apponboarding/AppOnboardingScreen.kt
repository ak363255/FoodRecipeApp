package com.example.foodrecipe.ui.composables.apponboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Girl
import androidx.compose.material.icons.filled.MobileFriendly
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.foodrecipe.R
import com.example.foodrecipe.ui.composables.apponboarding.viewmodel.AppOnboardingViewModel
import com.example.foodrecipe.ui.theme.RecipeAppColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch


@Composable
fun AppBoardingScreen(
    onActionCompleted: ()-> Unit,
    appOnboardingViewModel: AppOnboardingViewModel,
    modifier: Modifier = Modifier,
) {

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {
        pageList.size
    })
    val animateColor = animateColorAsState(targetValue = pageList[pagerState.currentPage].pageColor, animationSpec = tween(
        durationMillis = 700
    ))

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Blue, // dark color to show light icons
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = Color(0xFF3700B3),
            darkIcons = false
        )
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = RecipeAppColor.White)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .background(
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                    color = animateColor.value
                )

        ) {
            HorizontalPager(state = pagerState) {
                OnboardingPageView(
                    modifier = Modifier,
                    page = pageList[pagerState.currentPage]
                )
            }
        }
        if (pagerState.currentPage == pageList.size - 1) {
            val animateFloat = remember { androidx.compose.animation.core.Animatable(initialValue = 1f) }
            LaunchedEffect(Unit) {
                animateFloat.animateTo(0f, animationSpec = tween(durationMillis = 500))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.BottomCenter)
                    .graphicsLayer{
                        translationY = 20.dp.toPx()*animateFloat.value
                    },
                horizontalArrangement = Arrangement.Center

            ) {
                Text(
                    "Lets go",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = RecipeAppColor.White
                    ),
                    modifier = Modifier
                        .clickable {
                            onActionCompleted()
                        }
                        .background(color = RecipeAppColor.Green)
                        .padding(horizontal = 32.dp, vertical = 12.dp)

                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp, start = 24.dp, end = 24.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = "Skip",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = RecipeAppColor.Black
                    ),
                    modifier = Modifier.clickable {
                        onActionCompleted()
                    }
                )

                Text(

                    text = buildAnnotatedString {
                        append("Next ")
                        withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)){
                            append(">")
                        }
                    },
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = RecipeAppColor.Black
                    ),
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                )
            }
        }


    }
}

@Composable
fun OnboardingPageView(modifier: Modifier = Modifier, page: OnboardingPage) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {
        val (appIcon, pageIcon, title, subtitle) = createRefs()
        Image(
            painter = painterResource(R.drawable.ic_logo_icon),
            modifier = Modifier
                .constrainAs(appIcon) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
                .height(56.dp),
            contentDescription = null
        )
        Image(
            contentDescription = null,
            imageVector = page.pageIcon,
            modifier = Modifier
                .constrainAs(pageIcon) {
                    top.linkTo(appIcon.bottom, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(200.dp),
            contentScale = ContentScale.Crop

        )
        Text(
            text = page.pageTitle,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(pageIcon.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            style = TextStyle(
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            textAlign = TextAlign.Center,
            text = page.pageSubtitle,
            modifier = Modifier
                .constrainAs(subtitle) {
                    top.linkTo(title.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                },
            style = TextStyle(fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Normal)
        )

    }
}

sealed class OnboardingPage(
    val pageColor: Color,
    val pageTitle: String,
    val pageSubtitle: String,
    val pageIcon: ImageVector
) {
    data class OnboardingPage1(
        val color: Color = RecipeAppColor.Orange,
        val title: String = "Search For recipes",
        val subtitle: String = "Browse and find any recipe you are looking for. Also you can search by the ingredient you like.",
        val image: ImageVector = Icons.Default.MobileFriendly
    ) : OnboardingPage(
        color,
        title,
        subtitle,
        image
    )

    data class OnboardingPage2(
        val color: Color = RecipeAppColor.LightOrange,
        val title: String = "Saved your liked Recipes",
        val subtitle: String = "Save all your favorite recipes and get it later locally",
        val image: ImageVector = Icons.Default.Girl
    ) : OnboardingPage(
        color,
        title,
        subtitle,
        image
    )

    data class OnboardingPage3(
        val color: Color = RecipeAppColor.LightGreen,
        val title: String = "Add Your meal plan",
        val subtitle: String = "Plan for the whole week, add the ingredients, browse recipes and add it easily and fast",
        val image: ImageVector = Icons.Default.Timer
    ) : OnboardingPage(
        color,
        title,
        subtitle,
        image
    )
}

val pageList = listOf<OnboardingPage>(
    OnboardingPage.OnboardingPage1(),
    OnboardingPage.OnboardingPage2(),
    OnboardingPage.OnboardingPage3(),
)