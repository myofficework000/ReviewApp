package com.code4galaxy.reviewnow.model

import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.view.feature.admin.home.AddBrandScreen
import com.code4galaxy.reviewnow.view.feature.admin.home.AdminHomeScreen
import com.code4galaxy.reviewnow.view.feature.admin.home.FlaggedReviewScreen
import com.code4galaxy.reviewnow.view.feature.admin.home.ManageUsersScreen
import com.code4galaxy.reviewnow.view.feature.admin.home.UserReviewsScreen
import com.code4galaxy.reviewnow.view.navigation.Screen

//composable(Screen.ManageBrands.route) { AddBrandScreen() }
//composable(Screen.ADMIN.route) { AdminHomeScreen() }
//composable(Screen.FlaggedReviews.route) { FlaggedReviewScreen() }
//composable(Screen.UserReviews.route) { UserReviewsScreen() }
//composable(Screen.ManageUsers.route) { ManageUsersScreen() }

enum class AdminNavigationItem(
    val title: String,
    val icon: Int,
    val route:String
) {
    ManageUsers(
        icon = R.drawable.home,
        title = "Manage Users",
        route=Screen.ManageUsers.route
    ),
    UserReviews(
        icon = R.drawable.review,
        title = "Reviews",
        route=Screen.UserReviews.route
    ),
    AdminHome(
        icon = R.drawable.settings,
        title = "Settings",
        route=Screen.ADMIN.route
    ),

}