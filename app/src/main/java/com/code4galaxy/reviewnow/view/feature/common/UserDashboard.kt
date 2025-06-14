//package com.code4galaxy.reviewnow.view.feature.common
//
//
//
// TODO do not use this file we should delete if you have some code from here please remove it and update your task accordingly
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.outlined.Add
//
//import androidx.compose.material.icons.outlined.Settings
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.LifecycleCoroutineScope
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.code4galaxy.reviewnow.view.feature.user.home.HomeScreen
//import com.code4galaxy.reviewnow.view.feature.user.reviews.MyReviewsScreen
//import com.code4galaxy.reviewnow.view.feature.user.profile.ProfileScreen
//import com.code4galaxy.reviewnow.view.navigation.AppNavGraph
//import com.code4galaxy.reviewnow.view.navigation.Screen
//import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel
//import com.code4galaxy.reviewnow.viewmodel.ThemeViewModel
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun UserDashboard(navController: NavHostController, navigationViewModel: NavigationViewModel) {
//
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//
//
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            ModalDrawerSheet {
//                Column(
//                    modifier = Modifier
//                        .padding(horizontal = 16.dp)
//                        .verticalScroll(rememberScrollState())
//                ) {
//                    Spacer(Modifier.height(12.dp))
//                    Text(
//                        text = "ReviewConnect",
//                        modifier = Modifier.padding(16.dp),
//                        style = MaterialTheme.typography.titleLarge
//                    )
//                    HorizontalDivider()
//
//                    Text("Navigate", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
//
//                    NavigationDrawerItem(
//                        label = { Text("Home") },
//                        selected = false,
//                        onClick = {
//                            navController.navigate(Screen.Home.route)
//                            scope.launch { drawerState.close() }
//                        }
//                    )
//                    NavigationDrawerItem(
//                        label = { Text("My Reviews") },
//                        selected = false,
//                        onClick = {
//                            navController.navigate(Screen.MyReviews.route)
//                            scope.launch { drawerState.close() }
//                        }
//                    )
//                    NavigationDrawerItem(
//                        label = { Text("Profile") },
//                        selected = false,
//                        onClick = {
//                            navController.navigate(Screen.Profile.route)
//                            scope.launch { drawerState.close() }
//                        }
//                    )
//
//                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
//
//                    Text("More", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
//                    NavigationDrawerItem(
//                        label = { Text("Settings") },
//                        selected = false,
//                        icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
//                        onClick = { /* Todo handle here... */ }
//                    )
//                    NavigationDrawerItem(
//                        label = { Text("Help & Feedback") },
//                        selected = false,
//                        icon = { Icon(Icons.Outlined.Add, contentDescription = null) },
//                        onClick = { /* Todo handle here... */ }
//                    )
//                    Spacer(Modifier.height(12.dp))
//                }
//            }
//        }
//    ) {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("User Dashboard") },
//                    navigationIcon = {
//                        IconButton(onClick = {
//                            scope.launch {
//                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
//                            }
//                        }) {
//                            Icon(Icons.Default.Menu, contentDescription = "Menu")
//                        }
//                    }
//                )
//            }
//        ) { innerPadding ->
//
//            val themeViewModel: ThemeViewModel = hiltViewModel()
//            AppNavGraph(navController,navigationViewModel,themeViewModel, modifier = Modifier.padding(innerPadding))
//
//        }
//    }
//}
//
