package com.code4galaxy.reviewnow.model.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.code4galaxy.reviewnow.view.theme.AppTheme

class ThemePreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "app_preferences"
        private const val KEY_THEME = "selected_theme"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveTheme(theme: AppTheme) {
        prefs.edit {
            putString(KEY_THEME, theme.name)
        }
    }


    fun getTheme(): AppTheme {
        return AppTheme.entries.firstOrNull {
            it.name == prefs.getString(KEY_THEME, AppTheme.SYSTEM_DEFAULT.name)
        } ?: AppTheme.SYSTEM_DEFAULT
    }
}
