package com.code4galaxy.reviewnow.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.code4galaxy.reviewnow.view.navigation.SetUpAppLaunch
import com.code4galaxy.reviewnow.view.theme.AppThemeWrapper
import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel

class MainActivity : ComponentActivity() {
    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppThemeWrapper {
                SetUpAppLaunch(navigationViewModel)
            }
        }
    }
}

