package com.code4galaxy.reviewnow.view.navigation


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
    object Login : Screen("login")
    object Register : Screen("register")

    // USER
    object Home : Screen("user_home")
    object BrandDetail : Screen("user_brand_detail/{brandId}") {
        fun pass(brandId: String) = "user_brand_detail/$brandId"
    }

    object SubmitReview : Screen("submit_review/{brandId}") {
        fun pass(brandId: String) = "submit_review/$brandId"
    }

    object MyReviews : Screen("my_reviews")
    object Profile : Screen("profile")

    // ADMIN
    object AdminDashboard : Screen("admin_dashboard")
    object ManageUsers : Screen("manage_users")
    object ManageBrands : Screen("manage_brands")
    object FlaggedReviews : Screen("flagged_reviews")
}
