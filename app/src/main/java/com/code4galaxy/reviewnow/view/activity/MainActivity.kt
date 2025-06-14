package com.code4galaxy.reviewnow.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.code4galaxy.reviewnow.view.navigation.SetUpAppLaunch
import com.code4galaxy.reviewnow.view.theme.AppThemeWrapper
import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel
import com.code4galaxy.reviewnow.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val navigationViewModel: NavigationViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppThemeWrapper(themeViewModel = themeViewModel) {
                SetUpAppLaunch(
                    navigationViewModel = navigationViewModel,
                    themeViewModel = themeViewModel // ✅ Pass it here
                )
            }
        }
    }
}
