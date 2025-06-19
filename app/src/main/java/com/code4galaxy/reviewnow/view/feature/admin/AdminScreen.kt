package com.code4galaxy.reviewnow.view.feature.admin

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.code4galaxy.reviewnow.model.AdminNavigationItem
import com.code4galaxy.reviewnow.model.CustomDrawerState
import com.code4galaxy.reviewnow.model.isOpened
import com.code4galaxy.reviewnow.model.opposite
import com.code4galaxy.reviewnow.view.component.AdminCustomDrawer
import com.code4galaxy.reviewnow.view.util.coloredShadow
import com.code4galaxy.reviewnow.view.feature.admin.home.*
import com.code4galaxy.reviewnow.view.feature.user.settings.SettingsScreen
import com.code4galaxy.reviewnow.view.navigation.Graph
import com.code4galaxy.reviewnow.view.navigation.Screen
import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel
import com.code4galaxy.reviewnow.viewmodel.ThemeViewModel
import kotlin.math.roundToInt

@Composable
fun AdminScreen(
    rootNavController: NavHostController,
    navigationViewModel: NavigationViewModel,
    themeViewModel: ThemeViewModel
) {
    var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }
    var selectedNavigationItem by remember { mutableStateOf(AdminNavigationItem.AdminHome) }
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
        AdminCustomDrawer(
            selectedNavigationItem = selectedNavigationItem,
            onNavigationItemClick = {
                selectedNavigationItem = it
                innerNavController.navigate(it.route)
                drawerState = CustomDrawerState.Closed
            },
            onCloseClick = { drawerState = CustomDrawerState.Closed }
        )
        AdminContent(
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
            navController = innerNavController,
            rootNavController = rootNavController,
            navigationViewModel = navigationViewModel,
            themeViewModel = themeViewModel
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminContent(
    modifier: Modifier = Modifier,
    drawerState: CustomDrawerState,
    onDrawerClick: (CustomDrawerState) -> Unit,
    navController: NavHostController,
    rootNavController: NavHostController,
    navigationViewModel: NavigationViewModel,
    themeViewModel: ThemeViewModel
) {
    Scaffold(
        modifier = modifier.clickable(enabled = drawerState.isOpened()) {
            onDrawerClick(CustomDrawerState.Closed)
        },
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(text = "")
                    }
                },
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
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.ADMIN.route,
                route = Graph.USER
            ) {
                composable(Screen.ADMIN.route) {
                    AdminHomeScreen(
                        onManageUsersClick = {
                            navController.navigate(Screen.ManageUsers.route)
                        },
                        onModerateReviewsClick = {
                            navController.navigate(Screen.UserReviews.route)
                        },
                        onAddBrandClick = {
                            navController.navigate(Screen.ManageBrands.route)
                        },
                        onFlaggedReviewsClick = {
                            navController.navigate(Screen.FlaggedReviews.route)
                        },
                        onLogoutNavigate = {
                            rootNavController.navigate(Graph.AUTH) {
                                popUpTo(0)
                            }
                        }
                    )
                }
                composable(Screen.ManageUsers.route) { ManageUsersScreen() }
                composable(Screen.UserReviews.route) { UserReviewsScreen() }
                composable(Screen.FlaggedReviews.route) { FlaggedReviewScreen() }
                composable(Screen.ManageBrands.route) { AddBrandScreen() }
                composable(Screen.Settings.route) {
                     SettingsScreen(themeViewModel = themeViewModel)
                }
            }
        }
    }
}
