package com.code4galaxy.reviewnow.view.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.code4galaxy.reviewnow.view.navigation.SetUpAppLaunch
import com.code4galaxy.reviewnow.view.theme.AppThemeWrapper
import com.code4galaxy.reviewnow.view.feature.user.util.getSavedLanguageCode
import com.code4galaxy.reviewnow.view.feature.user.util.updateLocale
import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel
import com.code4galaxy.reviewnow.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun attachBaseContext(newBase: Context) {
        val savedLangCode = getSavedLanguageCode(newBase)
        val updatedContext = updateLocale(newBase, savedLangCode)
        super.attachBaseContext(updatedContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val selectedTheme by themeViewModel.theme.collectAsState()

            AppThemeWrapper(selectedTheme = selectedTheme) {
                SetUpAppLaunch(
                    navigationViewModel = navigationViewModel,
                    themeViewModel = themeViewModel
                )
            }
        }
    }
}
