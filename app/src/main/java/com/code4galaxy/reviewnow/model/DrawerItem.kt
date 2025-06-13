package com.code4galaxy.reviewnow.model

import com.code4galaxy.reviewnow.view.navigation.Screen

sealed class DrawerItem(val title: String, val route: String) {
    object Home : DrawerItem("Home", Screen.Home.route)
    object MyReviews : DrawerItem("My Reviews", Screen.MyReviews.route)
    object Profile : DrawerItem("Profile", Screen.Profile.route)
}
