//package com.code4galaxy.reviewnow.view.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.code4galaxy.reviewnow.model.constants.ADMIN_TYPE
//import com.code4galaxy.reviewnow.model.constants.USER_TYPE
//import com.code4galaxy.reviewnow.view.feature.common.splash.SplashScreen
//import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel
//import com.code4galaxy.reviewnow.viewmodel.ThemeViewModel
//
/// TODO do not use this file we should delete if you have some code from here please remove it and update your task accordingly
////
//@Composable
//fun AppNavGraph(navController:NavHostController,navigationViewModel: NavigationViewModel,themeViewModel: ThemeViewModel,modifier: Modifier=Modifier) {
//
//    NavHost(
//        navController=navController,
//        startDestination = Screen.Splash.route,
//        modifier = modifier
//    ){
//
//        authNavGraph(navController)
//        userNavGraph(navController,navigationViewModel,themeViewModel)
//        adminNavGraph(navController)
//
//        composable(route = Screen.Splash.route){
//            SplashScreen({
//                when(navigationViewModel.getUserType()){
//                    USER_TYPE ->{
//                        navController.navigate(Graph.USER){
//                            popUpTo(Screen.Splash.route){inclusive=true}
//                        }
//                    }
//                    ADMIN_TYPE ->{
//                        navController.navigate(Graph.ADMIN){
//                            popUpTo(Screen.Splash.route){inclusive=true}
//                        }
//                    }
//                    else->{
//                        navController.navigate(Graph.AUTH){
//                            popUpTo(Screen.Splash.route){inclusive=true}
//                        }
//                    }
//
//
//                }
//            })
//        }
//
//    }
//
//}