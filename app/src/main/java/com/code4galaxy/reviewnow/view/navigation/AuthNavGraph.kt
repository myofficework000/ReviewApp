package com.code4galaxy.reviewnow.view.navigation

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.code4galaxy.reviewnow.model.data.local.preferences.UserPreferenceManager
import com.code4galaxy.reviewnow.view.composables.LoginScreen
import com.code4galaxy.reviewnow.view.composables.RegisterScreen

import com.code4galaxy.reviewnow.view.feature.common.authenticaton.WelcomeScreen



fun NavGraphBuilder.authNavGraph(navController:NavHostController, context: Context){
    val userPrefManager = UserPreferenceManager(context)

    navigation(route = Graph.AUTH, startDestination = Screen.Welcome.route){

        composable(route=Screen.Login.route){
            LoginScreen(
                navController = navController, // pass navController explicitly
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
                onSignInClick = {
                    // TODO: Decide userType from shared preferences or auth state
                    // For example, navigate to admin or user screen
                    val userType = userPrefManager.getUserType()
                        if (userType == "admin") {
                            navController.navigate(Screen.ADMIN.route) {
                                popUpTo(Graph.USER) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Screen.USER.route) {
                                popUpTo(Graph.USER) { inclusive = true }
                            }
                        }
                }
            )

        }

        composable(route=Screen.Register.route){
            RegisterScreen({
                navController.navigate(Screen.Login.route)
            },
                navController = navController
            )
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