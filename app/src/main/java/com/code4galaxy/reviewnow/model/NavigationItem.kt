package com.code4galaxy.reviewnow.model

import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.view.navigation.Screen


enum class NavigationItem(
    val title: String,
    val icon: Int,
    val route:String
) {
    Home(
        icon = R.drawable.home,
        title = "Home",
        route=Screen.Home.route
    ),
    Profile(
        icon = R.drawable.profile,
        title = "Profile",
        route=Screen.Profile.route
    ),
    Reviews(
        icon = R.drawable.reviews,
        title = "Reviews",
        route=Screen.MyReviews.route
    ),
    Settings(
        icon = R.drawable.settings,
        title = "Settings",
        route=Screen.Settings.route
    ),

}