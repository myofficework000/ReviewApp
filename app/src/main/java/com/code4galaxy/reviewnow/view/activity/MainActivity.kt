package com.code4galaxy.reviewnow.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.code4galaxy.reviewnow.view.feature.user.MainScreen
import com.code4galaxy.reviewnow.view.navigation.SetUpAppLaunch
import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           SetUpAppLaunch(navigationViewModel)
//            MainScreen()
        }
    }
}

