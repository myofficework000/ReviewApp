package com.code4galaxy.reviewnow.view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.code4galaxy.reviewnow.viewmodel.ThemeViewModel

@Composable
fun AppThemeWrapper(
    themeViewModel: ThemeViewModel,
    content: @Composable () -> Unit
) {
    val selectedTheme by themeViewModel.theme.collectAsState()
    val isDark = when (selectedTheme) {
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
        AppTheme.SYSTEM -> isSystemInDarkTheme()
    }

    MaterialTheme(
        colorScheme = if (isDark) darkColorScheme() else lightColorScheme(),
        typography = Typography(),
        content = content
    )
}
