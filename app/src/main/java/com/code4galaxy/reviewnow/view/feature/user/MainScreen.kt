package com.code4galaxy.reviewnow.view.feature.user


import android.R.attr.navigationIcon
import android.R.attr.title
import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.code4galaxy.reviewnow.model.CustomDrawerState
import com.code4galaxy.reviewnow.model.NavigationItem
import com.code4galaxy.reviewnow.model.isOpened
import com.code4galaxy.reviewnow.model.opposite
import com.code4galaxy.reviewnow.view.util.coloredShadow
import com.code4galaxy.reviewnow.view.component.CustomDrawer
import com.code4galaxy.reviewnow.view.feature.user.brand.BrandDetailScreen
import com.code4galaxy.reviewnow.view.feature.user.home.HomeScreen
import com.code4galaxy.reviewnow.view.feature.user.profile.ProfileScreen
import com.code4galaxy.reviewnow.view.feature.user.reviews.MyReviewsScreen
import com.code4galaxy.reviewnow.view.feature.user.settings.SettingsScreen
import com.code4galaxy.reviewnow.view.feature.user.submit_review.SubmitReviewScreen

import com.code4galaxy.reviewnow.view.navigation.Graph
import com.code4galaxy.reviewnow.view.navigation.Screen
import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel
import com.code4galaxy.reviewnow.viewmodel.ThemeViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.roundToInt

@Composable
fun MainScreen(navController: NavHostController, navigationViewModel: NavigationViewModel,themeViewModel: ThemeViewModel= hiltViewModel()) {
    var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }
    var selectedNavigationItem by remember { mutableStateOf(NavigationItem.Home) }
    val innerNavController = rememberNavController()
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density

    val screenWidth = remember {
        derivedStateOf { (configuration.screenWidthDp * density).roundToInt() }
    }
    val offsetValue by remember { derivedStateOf { (screenWidth.value / 4.5).dp } }
    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
        label = "Animated Offset"
    )
    val animatedScale by animateFloatAsState(
        targetValue = if (drawerState.isOpened()) 0.9f else 1f,
        label = "Animated Scale"
    )

    BackHandler(enabled = drawerState.isOpened()) {
        drawerState = CustomDrawerState.Closed
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        CustomDrawer(
            selectedNavigationItem = selectedNavigationItem,
            onNavigationItemClick = {
                selectedNavigationItem = it
               innerNavController.navigate( it.route)
                drawerState = CustomDrawerState.Closed
            },
            onCloseClick = { drawerState = CustomDrawerState.Closed }
        )
        MainContent(
            modifier = Modifier
                .offset(x = animatedOffset)
                .scale(scale = animatedScale)
                .coloredShadow(
                    color = Color.Black,
                    alpha = 0.1f,
                    shadowRadius = 50.dp
                ),
            drawerState = drawerState,
            onDrawerClick = { drawerState = it },
            navController=innerNavController,
            themeViewModel=themeViewModel,
            navigationViewModel=navigationViewModel
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    drawerState: CustomDrawerState,
    onDrawerClick: (CustomDrawerState) -> Unit,
    navController: NavHostController,
    navigationViewModel: NavigationViewModel,
    themeViewModel: ThemeViewModel
) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier
            .clickable(enabled = drawerState == CustomDrawerState.Opened) {
                onDrawerClick(CustomDrawerState.Closed)
            },
        topBar = {
            if (currentRoute != Screen.SubmitReview.route) {
                TopAppBar(
                    title = { Text(text = "") },
                    navigationIcon = {
                        IconButton(onClick = { onDrawerClick(drawerState.opposite()) }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu Icon"
                            )
                        }
                    }
                )
            }
        }

    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // TODO unnecessary
//            AppNavGraph(navController,navigationViewModel,themeViewModel)
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                route = Graph.USER
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(){brandId: String ->
                        navController.navigate(Screen.BrandDetail.pass(brandId))

                    }
                }

                composable(
                    route = Screen.BrandDetail.route,
                    arguments = listOf(navArgument("brandId") { type = NavType.StringType })
                ) {
                    val brandId = it.arguments?.getString("brandId") ?: ""
                    BrandDetailScreen(brandId = brandId, onSubmit = {
                        navController.navigate(Screen.SubmitReview.pass(brandId))
                    })
                }
                composable(
                    route = Screen.SubmitReview.route,
                    arguments = listOf(navArgument("brandId") { type = NavType.StringType })
                ) {

                    val brandId = it.arguments?.getString("brandId") ?: ""
                    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

                    SubmitReviewScreen(
                        brandId = brandId,
                        userId = userId,
                        navController = navController
                    )



                }

                composable(Screen.MyReviews.route) {
                    MyReviewsScreen()
                }

                composable(Screen.Profile.route) {
                    ProfileScreen()
                }

                composable(Screen.Settings.route) {
                    SettingsScreen(themeViewModel = themeViewModel)
                }

            }
        }
    }
}