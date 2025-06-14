package com.code4galaxy.reviewnow.view.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.code4galaxy.reviewnow.view.composables.LoginScreen
import com.code4galaxy.reviewnow.view.composables.RegisterScreen

import com.code4galaxy.reviewnow.view.feature.common.authenticaton.WelcomeScreen


fun NavGraphBuilder.authNavGraph(navController:NavHostController){
    navigation(route = Graph.AUTH, startDestination = Screen.Welcome.route){
        composable(route=Screen.Login.route){
            LoginScreen({
                navController.navigate(Screen.Register.route)
            },
                {
                    navController.navigate(Graph.USER)
                }
            )
        }

        composable(route=Screen.Register.route){
            RegisterScreen({
                navController.navigate(Screen.Login.route)
            })
        }

        composable(route=Screen.Welcome.route){
            WelcomeScreen{
                navController.navigate(Screen.Login.route)
            }
        }

    }
}