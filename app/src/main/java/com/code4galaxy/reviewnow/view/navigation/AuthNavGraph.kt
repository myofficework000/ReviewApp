package com.code4galaxy.reviewnow.view.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.code4galaxy.reviewnow.view.feature.common.authenticaton.LoginScreen
import com.code4galaxy.reviewnow.view.feature.common.authenticaton.RegisterScreen
import com.code4galaxy.reviewnow.view.feature.common.authenticaton.WelcomeScreen


fun NavGraphBuilder.authNavGraph(navController:NavHostController){
    navigation(route = Graph.AUTH, startDestination = Screen.Login.route){
        composable(route=Screen.Login.route){
            LoginScreen{
                navController.navigate(Screen.Register.route)
            }
        }

        composable(route=Screen.Register.route){
            RegisterScreen()
        }

        composable(route=Screen.Welcome.route){
            WelcomeScreen()
        }

    }
}