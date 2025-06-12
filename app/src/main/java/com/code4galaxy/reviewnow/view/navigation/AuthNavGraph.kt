package com.code4galaxy.reviewnow.view.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.code4galaxy.reviewnow.view.composables.LoginScreen
import com.code4galaxy.reviewnow.view.feature.common.authenticaton.RegisterScreen


fun NavGraphBuilder.authNavGraph(navController:NavHostController){
    navigation(route = Graph.AUTH, startDestination = Screen.Login.route){
        composable(route=Screen.Login.route){
            LoginScreen()
        }

        composable(route=Screen.Register.route){
            RegisterScreen()
        }

    }
}