package com.code4galaxy.reviewnow.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.code4galaxy.reviewnow.model.constants.ADMIN_TYPE
import com.code4galaxy.reviewnow.model.constants.USER_TYPE
import com.code4galaxy.reviewnow.view.feature.common.splash.SplashScreen
import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel


@Composable
fun AppNavGraph(navController:NavHostController,navigationViewModel: NavigationViewModel,modifier: Modifier=Modifier) {

    NavHost(
        navController=navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ){

        userNavGraph(navController,navigationViewModel)
//
//        authNavGraph(navController)
//        userNavGraph(navController,navigationViewModel)
//        adminNavGraph(navController)

    }

}