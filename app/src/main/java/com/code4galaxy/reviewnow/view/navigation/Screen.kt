package com.code4galaxy.reviewnow.view.navigation

sealed class Screenn(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object Profile : Screen("profile")
    object Settings : Screen("settings")

    object ADMIN : Screen("admin_home")
    object ManageUsers : Screen("manage_users")
    object ManageBrands : Screen("add_brand")
    object FlaggedReviews : Screen("flagged_reviews")
    object UserReviews : Screen("user_reviews")
    object Welcome : Screen("welcome")
}