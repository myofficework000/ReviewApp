package com.code4galaxy.reviewnow.view.navigation

import com.code4galaxy.reviewnow.view.feature.admin.home.AddBrandScreen
import com.code4galaxy.reviewnow.view.feature.admin.home.AdminHomeScreen
import com.code4galaxy.reviewnow.view.feature.admin.home.FlaggedReviewScreen
import com.code4galaxy.reviewnow.view.feature.admin.home.ManageUsersScreen
import com.code4galaxy.reviewnow.view.feature.admin.home.UserReviewsScreen


object Graph {
    const val ROOT = "root_graph"
    const val USER = "user_graph"
    const val ADMIN = "admin_graph"
    const val AUTH = "auth_graph"
}

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    // TODO add auth graph

    //AUTH
    object AUTH : Screen("auth")
    object Login : Screen("login")
    object Register : Screen("register")
    object Welcome : Screen("welcome")


    object UserDashboard : Screen("user_dashboard")

    // USER
    object USER : Screen("user")
    object Home : Screen("user_home")
    object BrandDetail : Screen("user_brand_detail/{brandId}") {
        fun pass(brandId: String) = "user_brand_detail/$brandId"
    }

    object SubmitReview : Screen("submit_review/{brandId}") {
        fun pass(brandId: String) = "submit_review/$brandId"
    }
    object Settings : Screen("settings")
    object MyReviews : Screen("my_reviews")
    object Profile : Screen("profile")

    // ADMIN
    object ADMIN : Screen("admin")
    object AdminDashboard : Screen("admin_dashboard")
    object ManageUsers : Screen("manage_users")
    object ManageBrands : Screen("manage_brands")
    object FlaggedReviews : Screen("flagged_reviews")
    object UserReviews : Screen("user_reviews")





}
