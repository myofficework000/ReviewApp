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

                    // TODO Abeer: fetch data user type from shared preferences accordingly you should go to user or admin side
                    navController.navigate(Screen.ADMIN.route){
                        popUpTo(Graph.USER){ inclusive=true}

                    }
                }
            )
        }

        composable(route=Screen.Register.route){
            RegisterScreen({
                navController.navigate(Screen.Login.route)
            })
        }

        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                onClick = { navController.navigate(Screen.Login.route) },
                onGoogleSignInSuccess = { navController.navigate(Screen.USER.route){
                    popUpTo(Graph.USER){ inclusive=true}

                } }
            )
        }

    }
}