package com.code4galaxy.reviewnow.view.theme

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun AppThemeWrapper(
    selectedTheme: AppTheme,
    content: @Composable () -> Unit
) {
    val isDark = when (selectedTheme) {
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
        AppTheme.SYSTEM -> isSystemInDarkTheme()
    }

    Log.d("ThemeDebug", "Applying theme: $selectedTheme")

    MaterialTheme(
        colorScheme = if (isDark) darkColorScheme() else lightColorScheme(),
        typography = Typography(),
        content = content
    )
}
