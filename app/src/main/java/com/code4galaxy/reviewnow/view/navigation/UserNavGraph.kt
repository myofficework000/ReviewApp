package com.code4galaxy.reviewnow.view.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.code4galaxy.reviewnow.view.feature.common.UserDashboard
import com.code4galaxy.reviewnow.view.feature.user.brand.BrandDetailScreen
import com.code4galaxy.reviewnow.view.feature.user.home.HomeScreen
import com.code4galaxy.reviewnow.view.feature.user.profile.ProfileScreen
import com.code4galaxy.reviewnow.view.feature.user.reviews.MyReviewsScreen
import com.code4galaxy.reviewnow.view.feature.user.settings.SettingsScreen
import com.code4galaxy.reviewnow.view.feature.user.submit_review.SubmitReviewScreen
import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel

fun NavGraphBuilder.userNavGraph(
    navController: NavHostController,
    navigationViewModel: NavigationViewModel
) {

    navigation(
        route = Graph.USER,
        startDestination = Screen.MyReviews.route
    ) {

        composable(Screen.UserDashboard.route) {
            UserDashboard(navController, navigationViewModel)
        }


        composable(Screen.Home.route) {
            HomeScreen(
                onBrandClick = { brandId ->
                    navController.navigate(Screen.BrandDetail.pass(brandId))
                }

            )
        }

        composable(
            route = Screen.BrandDetail.route,
            arguments = listOf(navArgument("brandId") { type = NavType.StringType })
        ) {
            val brandId = it.arguments?.getString("brandId") ?: ""
            BrandDetailScreen(brandId = brandId, onSubmit = {
                navController.navigate(Screen.SubmitReview.pass(brandId))
            })
        }

        composable(
            route = Screen.SubmitReview.route,
            arguments = listOf(navArgument("brandId") { type = NavType.StringType })
        ) {
            val brandId = it.arguments?.getString("brandId") ?: ""
            SubmitReviewScreen(brandId)
        }

        composable(Screen.MyReviews.route) {
            MyReviewsScreen()
        }

        composable(Screen.Profile.route) {
            ProfileScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }


}