package com.code4galaxy.reviewnow.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.code4galaxy.reviewnow.constants.ADMIN_TYPE
import com.code4galaxy.reviewnow.constants.USER_TYPE
import com.code4galaxy.reviewnow.view.feature.common.splash.SplashScreen
import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun AppNavGraph(navController:NavHostController,navigationViewModel: NavigationViewModel,modifier: Modifier=Modifier) {

    NavHost(
        navController=navController,
        startDestination = Screen.Splash.route,
        route = Graph.ROOT,
        modifier = modifier
    ){
        composable(route = Screen.Splash.route){
            SplashScreen({

                when(navigationViewModel.getUserType()){
                    USER_TYPE ->{
                        navController.navigate(Graph.USER){
                            popUpTo(Screen.Splash.route){inclusive=true}
                        }
                    }
                    ADMIN_TYPE ->{
                        navController.navigate(Graph.ADMIN){
                            popUpTo(Screen.Splash.route){inclusive=true}
                        }
                    }
                    else->{
                        navController.navigate(Graph.AUTH){
                            popUpTo(Screen.Splash.route){inclusive=true}
                        }
                    }


                }
            })
        }

        authNavGraph(navController)
        userNavGraph(navController,navigationViewModel)
        adminNavGraph(navController)

    }

}