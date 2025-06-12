package com.code4galaxy.reviewnow.view.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.code4galaxy.reviewnow.view.feature.admin.brands.BrandsScreen
import com.code4galaxy.reviewnow.view.feature.admin.users.AdminUsersScreen



fun NavGraphBuilder.adminNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.ADMIN,
        startDestination = Screen.AdminDashboard.route
    ) {

        composable(Screen.ManageUsers.route) { AdminUsersScreen() }
        composable(Screen.ManageBrands.route) { BrandsScreen() }

    }
}
