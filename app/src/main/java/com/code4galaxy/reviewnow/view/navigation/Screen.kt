package com.code4galaxy.reviewnow.view.navigation

sealed class Screenn(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}