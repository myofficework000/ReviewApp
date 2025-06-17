package com.code4galaxy.reviewnow.view.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.firebase.auth.FirebaseAuth

import com.code4galaxy.reviewnow.view.feature.user.brand.BrandDetailScreen
import com.code4galaxy.reviewnow.view.feature.user.home.HomeScreen
import com.code4galaxy.reviewnow.view.feature.user.profile.ProfileScreen
import com.code4galaxy.reviewnow.view.feature.user.reviews.MyReviewsScreen
import com.code4galaxy.reviewnow.view.feature.user.settings.SettingsScreen
import com.code4galaxy.reviewnow.view.feature.user.submit_review.SubmitReviewScreen
import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel
import com.code4galaxy.reviewnow.viewmodel.ThemeViewModel

fun NavGraphBuilder.userNavGraph(
    navController: NavHostController,
    navigationViewModel: NavigationViewModel,
    themeViewModel: ThemeViewModel
) {
    navigation(
        route = Graph.USER,
        startDestination = Screen.Home.route
    ) {


        composable(Screen.Home.route) {

          HomeScreen(){brandId: String ->
              navController.navigate(Screen.BrandDetail.pass(brandId))

          }
        }

        composable(
            route = Screen.BrandDetail.route,
            arguments = listOf(navArgument("brandId") { type = NavType.StringType })
        ) {
            val brandId = it.arguments?.getString("brandId") ?: ""
            BrandDetailScreen(
                brandId = brandId,
                onSubmit = {
                    navController.navigate(Screen.SubmitReview.pass(brandId))
                }
            )
        }

        composable(
            route = Screen.SubmitReview.route,
            arguments = listOf(navArgument("brandId") { type = NavType.StringType })
        ) {
            val brandId = it.arguments?.getString("brandId") ?: ""
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            SubmitReviewScreen(
                brandId = brandId,
                userId = userId,
                navController = navController
            )
        }

        composable(Screen.MyReviews.route) {
            MyReviewsScreen()
        }

        composable(Screen.Profile.route) {
            ProfileScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen(themeViewModel = themeViewModel)
        }

    }
}
