package com.code4galaxy.reviewnow.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.code4galaxy.reviewnow.view.composables.LoginScreen
import com.code4galaxy.reviewnow.view.feature.common.splash.SplashScreen
import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel

@Composable
fun SetUpAppLaunch(navigationViewModel: NavigationViewModel) {
    val navController = rememberNavController()
    val currentScreen = navigationViewModel.currentScreen.collectAsState()

    // Set up the app's initial launch screen
    LaunchedEffect(currentScreen) {
        navController.navigate(currentScreen.value?.route ?: Screen.Home.route)
    }

//    RootNavGraph(navController, navigationViewModel)
    AppNavGraph(navController,navigationViewModel)
}

@Composable
fun RootNavGraph(navController: NavHostController, navigationViewModel: NavigationViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route){
            SplashScreen(onTimeout = {
                navigationViewModel.navigateTo(Screen.Login)
            })
        }
        composable(Screen.Login.route) {
            LoginScreen()
        }
    }
}