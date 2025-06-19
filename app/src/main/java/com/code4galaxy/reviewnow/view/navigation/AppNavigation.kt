package com.code4galaxy.reviewnow.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.code4galaxy.reviewnow.model.constants.ADMIN_TYPE
import com.code4galaxy.reviewnow.model.constants.USER_TYPE
import com.code4galaxy.reviewnow.view.feature.admin.AdminScreen

import com.code4galaxy.reviewnow.view.feature.common.splash.SplashScreen
import com.code4galaxy.reviewnow.view.feature.user.MainScreen

import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel
import com.code4galaxy.reviewnow.viewmodel.ThemeViewModel

@Composable
fun SetUpAppLaunch(navigationViewModel: NavigationViewModel,themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    val currentScreen = navigationViewModel.currentScreen.collectAsState()

    MainScreen(navController,navigationViewModel,themeViewModel)
    RootNavGraph(navController, navigationViewModel,themeViewModel)
}

@Composable
fun RootNavGraph(navController: NavHostController, navigationViewModel: NavigationViewModel,themeViewModel: ThemeViewModel) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {

            SplashScreen({
                println(navigationViewModel.getUserType())
                when (navigationViewModel.getUserType()) {

                    USER_TYPE -> {
                        navController.navigate(Screen.USER.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }

                    ADMIN_TYPE -> {
                        navController.navigate(Screen.ADMIN.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }

                    else -> {
                        navController.navigate(Graph.AUTH) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }


                }
            })

        }

        composable(Screen.USER.route) {
            MainScreen(navController, navigationViewModel, themeViewModel  )
        }
        composable(Screen.ADMIN.route) {
            AdminScreen(navController, navigationViewModel)
        }

        authNavGraph(navController)

    }
}