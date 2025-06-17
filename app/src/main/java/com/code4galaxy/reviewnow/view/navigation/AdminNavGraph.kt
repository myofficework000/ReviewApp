package com.code4galaxy.reviewnow.view.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.code4galaxy.reviewnow.view.feature.admin.brands.BrandsScreen
import com.code4galaxy.reviewnow.view.feature.admin.home.AddBrandScreen
import com.code4galaxy.reviewnow.view.feature.admin.home.AdminHomeScreen
import com.code4galaxy.reviewnow.view.feature.admin.home.FlaggedReviewScreen
import com.code4galaxy.reviewnow.view.feature.admin.home.ManageUsersScreen
import com.code4galaxy.reviewnow.view.feature.admin.home.UserReviewsScreen
import com.code4galaxy.reviewnow.view.feature.admin.users.AdminUsersScreen



fun NavGraphBuilder.adminNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.ADMIN,
        startDestination = Screen.ADMIN.route
    ) {

        composable(Screen.ManageBrands.route) { AddBrandScreen() }
        composable(Screen.ADMIN.route) {
            AdminHomeScreen(
                onManageUsersClick = {
                    navController.navigate(Screen.ManageUsers.route)
                },
                onAddBrandClick = {
                    navController.navigate(Screen.ManageBrands.route)
                },
                onFlaggedReviewsClick = {
                    navController.navigate(Screen.FlaggedReviews.route)
                },
                onModerateReviewsClick = {
                    navController.navigate(Screen.UserReviews.route)
                },
                onLogoutNavigate = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(Screen.FlaggedReviews.route) { FlaggedReviewScreen() }
        composable(Screen.UserReviews.route) { UserReviewsScreen() }
        composable(Screen.ManageUsers.route) {
            ManageUsersScreen(
                onBackClick = { navController.popBackStack() }
            )
        }



    }
}
